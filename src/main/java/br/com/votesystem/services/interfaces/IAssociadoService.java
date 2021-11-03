package br.com.votesystem.services.interfaces;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.votesystem.domain.dto.AssociadoDTO;
import br.com.votesystem.domain.persistence.Associado;
import br.com.votesystem.enuns.CpfState;

/**
 * 
 * @author Alisson.Oliveira
 *
 */


public interface IAssociadoService {

    CpfState validateIdentification(final String cpf);

    Collection<Associado> findByCpf(final String cpf);

    Collection<Associado> findAll();

    Page<Associado> findAll(final Pageable page);

    Associado findById(Long id);

    Associado add(final Associado value) throws Exception;

    String add(AssociadoDTO value) throws Exception;

    Associado update(final Associado value) throws Exception;

    String update(final AssociadoDTO value) throws Exception;

    boolean remove(Long id) throws Exception;

    String remove(final AssociadoDTO value) throws Exception;

    boolean validate(final Associado value);

    <T> Set<String> validateEntity(T obj);

    boolean exists(Long id);

    AssociadoDTO convert(final Associado value) throws NullPointerException;

}