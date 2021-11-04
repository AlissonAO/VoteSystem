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

/**
 * 
 * @author Alisson.Oliveira
 *
 */
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


  
    public CpfState validateIdentification(final String identification) {
        return cpfValidatorService.checkCpf(identification);
    }

   
    public Associado findByCpf(String cpf) {
       return repository.findByCpf(cpf );
    }


    public Associado findById(String id) {
        return repository.findById(id).orElse(null);
    }


    public Collection<Associado> findAll() {
        return repository.findAll();
    }

   
    @Override
    public Page<Associado> findAll(final Pageable page) {
        return repository.findAll(page);
    }

  
    @Transactional
    public Associado add(final Associado value) throws Exception {
        Associado obj = null;

        try {
            if (findById(value.getId()) != null) {
                throw new EntityExistsException();
            }

            if (findByCpf(value.getCpf()) != null) {
                throw new Exception("Já existe associado com esse CPF");
            }

            obj = repository.save(value);
            logger.info("Associado {} {} adicionado", value.getId(), value.getCpf());
        } catch (Exception e) {
            logger.error("ERROR em adicionar {}", e, value);
            throw e;
        }

        return obj;
    }

   
    @Transactional
    public Associado update(final Associado value) throws Exception {
        Associado obj = null;

        try {
            if (findById(value.getId()) == null) {
                throw new EntityNotFoundException();
            }

            if (findByCpf(value.getCpf())!= null) {
                throw new Exception("Já existe associado com esse CPF");
            }

            obj = repository.save(value);
            logger.info("Associado {} {} updated", value.getId(), value.getCpf());
        } catch (Exception e) {
            logger.error("ERROR em UPDATE  {}", e, value);
            throw e;
        }

        return obj;
    }


    @Transactional
    public boolean remove(Long id) throws Exception {

        try {
            Associado obj = findById(id);
            if (obj == null) {
                throw new EntityNotFoundException();
            }

            repository.delete(obj);
            logger.info("Associate {} {} deletar", obj.getId(), obj.getCpf());
        } catch (Exception e) {
            logger.error("ERROR em DELETAR  {}", e, id);
            throw e;
        }

        return true;
    }

  
    @Override
    @Transactional
    public boolean validate(Associado value) {
        return value != null ;
    }


    public boolean exists( Long id) {
        return findById(id) != null;
    }

  
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

            if (findByCpf(associadoVO.getCpf()) != null) {
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

            if (findByCpf(value.getCpf()) != null) {
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