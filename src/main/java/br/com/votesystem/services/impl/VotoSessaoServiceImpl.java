package br.com.votesystem.services.impl;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import br.com.votesystem.domain.dto.VotacaoSessaoDTO;
import br.com.votesystem.domain.persistence.VotacaoSessao;
import br.com.votesystem.enuns.CRUD;
import br.com.votesystem.repositories.IVotSessaoRepository;
import br.com.votesystem.services.interfaces.IVotoAtaService;
import br.com.votesystem.services.interfaces.IVotoSessaoService;
import br.com.votesystem.services.interfaces.ISendMessage;
import br.com.votesystem.services.interfaces.IValidatorService;
import br.com.votesystem.util.Action;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Set;

/**
 * 
 * @author Alisson.Oliveira
 *
 */
@Service
public class VotoSessaoServiceImpl implements IVotoSessaoService {

    private final Logger logger;
    private final IVotSessaoRepository repository;
    private final IValidatorService validator;
    private final IVotoAtaService votoAtaService;
    private ISendMessage sendMessage;

    /**
     * Constructor
     *
     * @param logger
     * @param repository
     * @param validator
     * @param votoAtaService
     * @param sendMessage
     */
    public VotoSessaoServiceImpl(
            final Logger logger,
            final IVotSessaoRepository repository,
            final IValidatorService validator,
            final IVotoAtaService votoAtaService,
            final ISendMessage sendMessage
    ) {
        this.logger = logger;
        this.votoAtaService = votoAtaService;
        this.validator = validator;
        this.repository = repository;
        this.sendMessage = sendMessage;
    }

 
    @Override
    public VotacaoSessao findById(String id) {
        return repository.findById(Long.valueOf(id)).orElse(null);
    }


    @Override
    public boolean exists(final String id) {
        return findById(id) != null;
    }

 
    @Override
    public boolean validate(VotacaoSessao value) {
        return value != null && value.isValid() && validateEntity(value).isEmpty();
    }

  
    @Override
    public <T> Set<String> validateEntity(T obj) {
        return validator.validateEntity(obj);
    }

 
    @Override
    @Transactional
    public VotacaoSessao add(final VotacaoSessao value) throws Exception {
        VotacaoSessao obj = value;

        try {
            if (value == null) {
                throw new NullPointerException();
            }

            if (value.getVotacaoAta() == null) {
                throw new NullPointerException("Sessão de votação sem Ata");
            }

            if (!votoAtaService.exists(value.getVotacaoAta().getId())) {
                throw new EntityNotFoundException("Ata não encontrada " + value.getVotacaoAta().getId());
            }

            if (votoAtaService.findById(value.getVotacaoAta().getId()).getPoll() != null) {
                throw new EntityExistsException("Ata já tem sessão de votação " + value.getVotacaoAta().getId());
            }

            if (!validate(value)) {
                throw new ValidationException("Sessão de votação inválida");
            }

            obj = repository.save(value);
        } catch (Exception e) {
            logger.error("ERROR ao ADICIONAR  {}: ", e, obj);
            throw e;
        }

        return obj;
    }

 
    @Override
    public String add(final VotacaoSessaoDTO value) throws Exception {
        String id = null;

        try {
            if (value == null) {
                throw new NullPointerException();
            }

            if (value.getIdVotoAta() == null) {
                throw new NullPointerException("Sessão de votação sem Ata");
            }

            if (!votoAtaService.exists(Long.valueOf(value.getIdVotoAta()))) {
                throw new EntityNotFoundException("Ata não encontrada " + value.getIdVotoAta());
            }

            if(value.getId() == null) {
                value.setId(new  VotacaoSessao().getId().toString());
            }

            if (votoAtaService.findById(Long.valueOf(value.getIdVotoAta())).getPoll() != null) {
                throw new EntityExistsException("Ata já tem sessão de votação " + value.getIdVotoAta());
            }

            if (!validator.validate(value)) {
                throw new ValidationException("Sessão de votação inválida");
            }

            sendMessage.send(new Action<>(value, CRUD.CREATE));
            id = value.getId();
        } catch (Exception e) {
            logger.error("ERROR on VALIDATE PollMeeting {} to ADD: ", e, value);
            throw e;
        }

        return id;
    }

  
    @Override
    public VotacaoSessao convert(VotacaoSessaoDTO value) {
        VotacaoSessao obj = new VotacaoSessao();

        obj.setId(Long.valueOf(value.getId()));
        obj.setVotacaoAta(votoAtaService.findById(Long.valueOf(value.getIdVotoAta())));
        obj.adjustDuration(value.getDuracao());

        return obj;
    }
}
