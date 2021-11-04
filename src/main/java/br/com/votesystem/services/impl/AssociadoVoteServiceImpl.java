package br.com.votesystem.services.impl;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import br.com.votesystem.domain.dto.AssociadoVoteDTO;
import br.com.votesystem.domain.persistence.Associado;
import br.com.votesystem.domain.persistence.VotacaoAta;
import br.com.votesystem.domain.persistence.VotacaoSessao;
import br.com.votesystem.domain.persistence.VotoAssociado;
import br.com.votesystem.enuns.CRUD;
import br.com.votesystem.enuns.CpfState;
import br.com.votesystem.repositories.IAssociadoRepository;
import br.com.votesystem.repositories.IAssociadoVoteRepository;
import br.com.votesystem.repositories.IVotoAtaRepository;
import br.com.votesystem.repositories.IVotSessaoRepository;
import br.com.votesystem.services.interfaces.IAssociadoVotoService;
import br.com.votesystem.services.interfaces.ICpfValidatorService;
import br.com.votesystem.services.interfaces.ISendMessage;
import br.com.votesystem.services.interfaces.IValidatorService;
import br.com.votesystem.util.Action;

@Service
public class AssociadoVoteServiceImpl implements IAssociadoVotoService {

    private final Logger logger;
    private final IValidatorService validator;
    private final ICpfValidatorService cpfValidatorService;
    private final IAssociadoVoteRepository repository;
    private final IVotSessaoRepository votSessaoRepository;
    private final IVotoAtaRepository votoAtaRepository;
    private final IAssociadoRepository associadoRepository;
    private final ISendMessage sendMessage;

    /**
     * Constructor
     *
     * @param logger
     * @param validator
     * @param cpfValidatorService
     * @param repository
     * @param votSessaoRepository
     * @param votoAtaRepository
     * @param associadoRepository
     * @param sendMessage
     */
    public AssociadoVoteServiceImpl(
            final Logger logger,
            final IValidatorService validator,
            final ICpfValidatorService cpfValidatorService,
            final IAssociadoVoteRepository repository,
            final IVotSessaoRepository votSessaoRepository,
            final IVotoAtaRepository votoAtaRepository,
            final IAssociadoRepository associadoRepository,
            final ISendMessage sendMessage) {
        this.logger = logger;
        this.validator = validator;
        this.cpfValidatorService = cpfValidatorService;
        this.repository = repository;
        this.votoAtaRepository = votoAtaRepository;
        this.votSessaoRepository = votSessaoRepository;
        this.associadoRepository = associadoRepository;
        this.sendMessage = sendMessage;
    }

   /**
    * 
    */
    @Override
    public VotoAssociado findById(final String id) {
        return repository.findById(id).orElse(null);
    }

   
    @Override
    public boolean exists(final String id) {
        return findById(id) != null;
    }

   
    @Override
    public boolean validate(VotoAssociado value) {
        return value != null && value.isValid() && validateEntity(value).isEmpty();
    }

    
    @Override
    public <T> Set<String> validateEntity(T obj) {
        return validator.validateEntity(obj);
    }

    @Override
    @Transactional
    public VotoAssociado add(final VotoAssociado value) {
        VotoAssociado obj = value;

        try {
            if (value == null) {
                throw new NullPointerException("Voto nulo");
            }

            if (exists(value.getId().toString())) {
                throw new EntityExistsException("Voto já existe");
            }

            if (value.getPoll() == null) {
                throw new NullPointerException("Sessão nula");
            }

            VotacaoSessao poll = votSessaoRepository.getById(value.getPoll().getId());

            if (poll == null) {
                throw new EntityNotFoundException("Sessão não existe");
            }

            if (!poll.isOpen()) {
                throw new ValidationException("Sessão de voto encerrada");
            }

            if (value.getAssociado() == null || value.getAssociado().getId() == null) {
                throw new NullPointerException("Associado nulo");
            }

            Associado associate = associadoRepository.getById(value.getAssociado().getCpf());
            if (associate == null) {
                throw new EntityNotFoundException("Associado não existe");
            }

            if (poll.getVotos()
                    .stream()
                    .anyMatch(p -> p.getAssociado().getId().equals(value.getAssociado().getId()) ||
                            p.getAssociado().getCpf().equalsIgnoreCase(associate.getCpf()))) {
                throw new EntityExistsException("Associado já votou na sessão");
            }

            if (value.getVoto() == null) {
                throw new ValidationException("Voto do associado nulo");
            }

            obj.setAssociado(associate);
            obj.setPoll(poll);
            obj = repository.save(obj);
        } catch (Exception e) {
            logger.warn("Voto não pode ser computado {} Voto: {}, associado: {}, sessao: {}", e,
                    obj,
                    obj.getAssociado() != null ? obj.getAssociado() : null,
                    obj.getPoll() != null ? obj.getPoll() : null);
            throw e;
        }

        return obj;
    }

    
    @Override
    @Transactional
    public String add(final AssociadoVoteDTO value) throws Exception {
        String id = null;

        try {
            if (value == null) {
                throw new NullPointerException("Voto nulo");
            }

            // If not setted id, set
            if (value.getId() == null) {
                value.setId(new VotoAssociado().getId().toString());
            }

            if (!validator.validate(value)) {
                throw new ValidationException("Voto inválido");
            }

            if (exists(value.getId())) {
                throw new EntityExistsException("Voto já existe");
            }

            if (value.getMinuteMeetingId() == null) {
                throw new NullPointerException("Ata nula");
            }

            VotacaoAta minuteMeeting = votoAtaRepository.getById(value.getMinuteMeetingId());

            if (minuteMeeting == null) {
                throw new EntityNotFoundException("Ata não existe");
            }

            VotacaoSessao poll = minuteMeeting.getPoll();

            if (poll == null) {
                throw new EntityNotFoundException("Sessão não existe");
            }

            if (!poll.isOpen()) {
                throw new ValidationException("Sessão de voto encerrada");
            }

            if (value.getAssociateId() == null) {
                throw new NullPointerException("Associado nulo");
            }

            Associado associate = associadoRepository.getById(value.getAssociateId());

            if (associate == null) {
                throw new EntityNotFoundException("Associado não existe");
            }

            CompletableFuture<CpfState> cpfState = CompletableFuture.supplyAsync(() -> cpfValidatorService.checkCpf(associate.getCpf()));

            if (poll.getVotos()
                    .stream()
                    .anyMatch(p -> p.getAssociado().getId().equals(value.getAssociateId()))) {
                throw new EntityExistsException("Associado já votou na sessão");
            }

            if (value.getVote() == null) {
                throw new ValidationException("Voto do associado nulo");
            }

            if (cpfState.get(20, TimeUnit.SECONDS).equals(CpfState.NOT_FOUND)) {
                throw new EntityNotFoundException("CPF inexistente");
            }

            if (cpfState.get(20, TimeUnit.SECONDS).equals(CpfState.UNABLE)) {
                throw new ValidationException("CPF não permitido para votar");
            }

            id = value.getId();
            sendMessage.send(new Action<>(value, CRUD.CREATE));
        } catch (Exception e) {
            logger.warn("Voto não pode ser computado {} Voto: {}, associado: {}, minuto: {}", e,
                    value.getId(),
                    value.getAssociateId(),
                    value.getMinuteMeetingId());
            throw e;
        }

        return id;
    }

   
    @Transactional
    public VotoAssociado convert(AssociadoVoteDTO value) {
        VotoAssociado obj = new VotoAssociado();

        try {
            obj.setId(Long.valueOf(value.getId()));
            obj.setAssociado(associadoRepository.getById(value.getAssociateId()));
            obj.setPoll(votoAtaRepository.getById(value.getMinuteMeetingId()).getPoll());
            obj.setVoto(value.getVote());

        } catch (Exception e) {
            obj = null;
            logger.error("ERROR ao CONVERTE AssociatdoVotoDTO para AssociateVoto", e);
        }

        return obj;
    }

}
