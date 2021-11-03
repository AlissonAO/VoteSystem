package br.com.votesystem.services.impl;

import java.util.Collection;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.votesystem.domain.dto.AssociadoDTO;
import br.com.votesystem.domain.persistence.Associado;
import br.com.votesystem.enuns.CRUD;
import br.com.votesystem.enuns.CpfState;
import br.com.votesystem.repositories.IAssociadoRepository;
import br.com.votesystem.services.interfaces.IAssociadoService;
import br.com.votesystem.services.interfaces.ICpfValidatorService;
import br.com.votesystem.services.interfaces.ISendMessage;
import br.com.votesystem.services.interfaces.IValidatorService;
import br.com.votesystem.util.Action;

@Service
public class AssociadoServiceImpl implements IAssociadoService {

    private final IAssociadoRepository repository;
    private final ICpfValidatorService cpfValidatorService;
    private final Logger logger;
    private final IValidatorService validator;
    private final ISendMessage sendMessage;

    /**
     * Constructor
     *
     * @param repository
     * @param cpfValidatorService
     * @param logger
     * @param validator
     * @param sendMessage
     */
    @Autowired
    public AssociadoServiceImpl(final IAssociadoRepository repository,
                                final ICpfValidatorService cpfValidatorService,
                                final Logger logger,
                                final IValidatorService validator,
                                final ISendMessage sendMessage) {
        this.repository = repository;
        this.cpfValidatorService = cpfValidatorService;
        this.logger = logger;
        this.validator = validator;
        this.sendMessage = sendMessage;
    }


    /**
     * Validate Identification
     *
     * @param identification
     * @return state
     */
    public CpfState validateIdentification(final String identification) {
        return cpfValidatorService.checkCpf(identification);
    }

    /**
     * Return collection of Associate by identification
     *
     * @param cpf
     * @return associates
     */
    public Collection<Associado> findByCpf(String cpf) {
        Collection<Associado> items = repository
                .findByCpf(cpf );
        return items;
    }

    /**
     * Return Associate
     *
     * @param id
     * @return associate
     */
    public Associado findById(String id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * Return associates
     *
     * @return associates
     */
    public Collection<Associado> findAll() {
        return repository.findAll();
    }

    /**
     * Return associates page
     * @param page
     * @return page
     */
    @Override
    public Page<Associado> findAll(final Pageable page) {
        return repository.findAll(page);
    }

    /**
     * Add Associate
     *
     * @param value
     * @return associate added
     */
    @Transactional
    public Associado add(final Associado value) throws Exception {
        Associado obj = null;

        try {
            if (findById(value.getId()) != null) {
                throw new EntityExistsException();
            }

            if (!findByCpf(value.getCpf()).isEmpty()) {
                throw new Exception("Já existe associado com esse CPF");
            }

            obj = repository.save(value);
            logger.info("Associate {} {} added", value.getId(), value.getCpf());
        } catch (Exception e) {
            logger.error("ERROR on ADD Associate {}", e, value);
            throw e;
        }

        return obj;
    }

    /**
     * Update Associate
     *
     * @param value
     * @return associate updated
     */
    @Transactional
    public Associado update(final Associado value) throws Exception {
        Associado obj = null;

        try {
            if (findById(value.getId()) == null) {
                throw new EntityNotFoundException();
            }

            if (findByCpf(value.getCpf()).stream().filter(p -> !p.getId().equals(value.getId())).count() > 0) {
                throw new Exception("Já existe associado com esse CPF");
            }

            obj = repository.save(value);
            logger.info("Associate {} {} updated", value.getId(), value.getCpf());
        } catch (Exception e) {
            logger.error("ERROR on UPDATE Associate {}", e, value);
            throw e;
        }

        return obj;
    }

    /**
     * Delete Associate
     *
     * @param id
     * @return success
     */
    @Transactional
    public boolean remove(Long id) throws Exception {

        try {
            Associado obj = findById(id);
            if (obj == null) {
                throw new EntityNotFoundException();
            }

            repository.delete(obj);
            logger.info("Associate {} {} deleted", obj.getId(), obj.getCpf());
        } catch (Exception e) {
            logger.error("ERROR on DELETE Associate {}", e, id);
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
    public boolean validate(Associado value) {
        return value != null ;
    }


    /**
     * Check if entity exists
     *
     * @param id
     * @return exist
     */
    public boolean exists( Long id) {
        return findById(id) != null;
    }

    /**
     * Add AssociateDTO
     *
     * @param associadoVO
     * @return response
     */
    public String add(AssociadoDTO associadoVO) throws Exception {
        Long id = null;

        try {
            if (associadoVO == null) {
                throw new NullPointerException("Associado nulo para inserir");
            }

            if (!validateEntity(associadoVO).isEmpty()) {
                throw new ValidationException("Erro na validação do Associado para inserir");
            }

            if(associadoVO.getId() == null) {
                associadoVO.setId(new Associado().getId());
            }

            if (exists(associadoVO.getId())) {
                throw new EntityExistsException("Associado já cadastrado para inserir");
            }

            if (findByCpf(associadoVO.getCpf()).stream().filter(p -> !p.getId().equals(associadoVO.getId())).count() > 0) {
                throw new ValidationException("CPF duplicado");
            }

            sendMessage.send(new Action<>(associadoVO, CRUD.CREATE));
            id = associadoVO.getId();
        } catch (Exception e) {
            logger.error("ERROR on Validate AssociateDTO {} to ADD", e, associadoVO);
            throw e;
        }

        return id.toString();
    }

    /**
     * Update AssociateDTO
     *
     * @param value
     * @return response
     */
    public String update(final AssociadoDTO value) throws Exception {
        String id = null;

        try {
            if (value == null) {
                throw new NullPointerException("Associado nulo para atualizar");
            }

            if (!validateEntity(value).isEmpty()) {
                throw new ValidationException("Erro na validação do Associado para atualizar");
            }

            if(value.getId() == null) {
                throw new ValidationException("Associado sem id");
            }

            if (!exists(value.getId())) {
                throw new EntityNotFoundException("Associado não cadastrado");
            }

            if (findByCpf(value.getCpf()).stream().filter(p -> !p.getId().equals(value.getId())).count() > 0) {
                throw new ValidationException("CPF duplicado");
            }

            sendMessage.send(new Action<>(value, CRUD.UPDATE));
            id = value.getId().toString();
        } catch (Exception e) {
            logger.error("ERROR on Validate AssociateDTO {} to UPDATE", e, value);
            throw e;
        }

        return id;
    }

    /**
     * Remove AssociateDTO
     *
     * @param value
     * @return response
     */
    public String remove(final AssociadoDTO value) throws Exception {
        String id = null;

        try {
            if (value == null) {
                throw new NullPointerException("Associado nulo para excluir");
            }

            if(value.getId() == null) {
                throw new ValidationException("Associado sem id");
            }

            if (!exists(value.getId())) {
                throw new EntityNotFoundException("Associado não existe para excluir");
            }

            sendMessage.send(new Action<>(value, CRUD.DELETE));
            id = value.getId().toString();
        } catch (Exception e) {
            logger.error("ERROR on Validate AssociateDTO {} to REMOVE", e, value);
            throw e;
        }

        return id;
    }

    /**
     * Convert Associado to AssociateDTO
     *
     * @param value
     * @return associate
     * @throws NullPointerException
     */
    public AssociadoDTO convert(final Associado value) throws NullPointerException {
        AssociadoDTO obj = new AssociadoDTO();

        obj.setId(value.getId());
        obj.setCpf(value.getCpf());

        return obj;
    }


	 public <T> Set<String> validateEntity(T obj) {
        return validator.validateEntity(obj);
    }


	@Override
	public Associado findById(Long id) {
		return repository.findById(id);
	}



}