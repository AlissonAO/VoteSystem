package br.com.votesystem.services.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.votesystem.domain.dto.VotacaoAtaDTO;
import br.com.votesystem.domain.persistence.VotacaoAta;
import br.com.votesystem.enuns.CRUD;
import br.com.votesystem.repositories.IVotoAtaRepository;
import br.com.votesystem.services.interfaces.IVotoAtaService;
import br.com.votesystem.services.interfaces.ISendMessage;
import br.com.votesystem.services.interfaces.IValidatorService;
import br.com.votesystem.util.Action;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VotoAtaServiceImpl implements IVotoAtaService {

    private final IVotoAtaRepository repository;
    private final Logger logger;
    private final IValidatorService validator;
    private final ISendMessage sendMessage;

    @Autowired
    public VotoAtaServiceImpl(final IVotoAtaRepository repository,
                                    final Logger logger,
                                    final IValidatorService validator,
                                    final ISendMessage sendMessage) {
        this.repository = repository;
        this.logger = logger;
        this.validator = validator;
        this.sendMessage = sendMessage;
    }

    public VotacaoAta findById(Long id) {
        return repository.findById(id);
    }

 
    @Override
    public VotacaoAtaDTO convert(final VotacaoAta value) {
        VotacaoAtaDTO dto = new VotacaoAtaDTO();

        try {
            dto.setId(value.getId());
            dto.setDescricao(value.getDescricao());
            dto.setResumo(value.getResumo());

            try {
                dto.setVotosTotal(value.getPoll().countTotal());
                dto.setVotosSim(value.getPoll().countVotoSim());
                dto.setvotosNao(value.getPoll().countVotoNao());
            } catch (Exception e) {
                dto.setVotosTotal(0L);
                dto.setVotosSim(0L);
                dto.setvotosNao(0L);
            }

        } catch (Exception e) {
            dto = null;
            logger.error("ERROR ao CONVERTE", e);
        }


        return dto;
    }

    @Override
    public Collection<VotacaoAtaDTO> convert(final Collection<VotacaoAta> values) {
        return values.stream().filter(p -> p != null).map(p -> convert(p)).collect(Collectors.toSet());
    }

  
    @Override
    public Collection<VotacaoAta> findAll() {
        return repository.findAll();
    }

 
    @Override
    public Page<VotacaoAta> findAll(final Pageable pageable) {
        return repository.findAll(pageable);
    }

   
    @Override
    @Transactional
    public VotacaoAta add(VotacaoAta value) throws Exception {
        VotacaoAta obj = null;

        try {
            if (findById(value.getId()) != null) {
                throw new EntityExistsException();
            }

            obj = repository.save(value);
            logger.info("VotoAta {} adicioado", value.getId());
        } catch (Exception e) {
            logger.error("ERROR em adicionar", e);
            throw e;
        }

        return obj;
    }


    @Override
    @Transactional
    public VotacaoAta update(final VotacaoAta value) throws Exception {
        VotacaoAta obj = null;

        try {
            if (findById(value.getId()) == null) {
                throw new EntityNotFoundException();
            }

            obj = repository.save(value);
            logger.info("MinuteMeeting {} updated", value.getId());
        } catch (Exception e) {
            logger.error("ERROR no UPDATE ", e);
            throw e;
        }

        return obj;
    }


    @Override
    @Transactional
    public boolean remove(Long id) throws Exception {

        try {
            VotacaoAta obj = findById(id);
            if (obj == null) {
                throw new EntityNotFoundException();
            }

            repository.delete(obj);
            logger.info("VotoAta {} deletado", obj.getId());
        } catch (Exception e) {
            logger.error("ERROR em DELETAR ", e);
            throw e;
        }

        return true;
    }

    /**
     * Validate entity
     *
     * @param value
     * @return valid
     */
    @Override
    @Transactional
    public boolean validate(VotacaoAta value) {
        return value != null && value.isValid() && validateEntity(value).isEmpty();
    }

    /**
     * Check violations
     *
     * @param obj
     * @param <T>
     * @return violations
     */
    @Override
    public <T> Set<String> validateEntity(T obj) {
        return validator.validateEntity(obj);
    }

    /**
     * Check if entity exists
     *
     * @param id
     * @return exist
     */
    @Override
    public boolean exists(Long id) {
        return findById(id) != null;
    }

 
    @Override
    public String add(final VotacaoAtaDTO value) throws Exception {
        String id = null;

        try {
            if (value == null) {
                throw new NullPointerException("Ata nula para inserir");
            }

            if (!validateEntity(value).isEmpty()) {
                throw new ValidationException("Erro na validação da Ata para inserir");
            }

            if(value.getId() == null) {
                value.setId(new VotacaoAta().getId());
            }

            if (exists(value.getId())) {
                throw new EntityExistsException("Ata já cadastrada para inserir");
            }

            sendMessage.send(new Action<>(value, CRUD.CREATE));
            id = value.getId().toString();
        } catch (Exception e) {
            logger.error("ERROR AO VALIDAr {}", e, value);
            throw e;
        }

        return id;
    }

    @Override
    public String update(final VotacaoAtaDTO value) throws Exception {
        String id = null;

        try {
            if (value == null) {
                throw new NullPointerException("Ata nula para atualizar");
            }

            if (!validateEntity(value).isEmpty()) {
                throw new ValidationException("Erro na validação da Ata para atualizar");
            }

            if(value.getId() == null) {
                throw new ValidationException("Ata sem id");
            }

            if (!exists(value.getId())) {
                throw new EntityNotFoundException("Ata não cadastrada");
            }

            sendMessage.send(new Action<>(value, CRUD.UPDATE));
            id = value.getId().toString();
        } catch (Exception e) {
            logger.error("ERROR AO VALIDAR ", e, value);
            throw e;
        }

        return id;
    }

 
    @Override
    public String remove(final VotacaoAtaDTO value) throws Exception {
        String id = null;

        try {
            if (value == null) {
                throw new NullPointerException("Ata nula para excluir");
            }

            if(value.getId() == null) {
                throw new ValidationException("Ata sem id");
            }

            if (!exists(value.getId())) {
                throw new EntityNotFoundException("Ata não existe para excluir");
            }

            sendMessage.send(new Action<>(value, CRUD.DELETE));
            id = value.getId().toString();
        } catch (Exception e) {
            logger.error("ERROR ao VALIDAR {}", e, value);
            throw e;
        }

        return id;
    }

}
