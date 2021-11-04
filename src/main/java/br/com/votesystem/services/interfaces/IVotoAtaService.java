package br.com.votesystem.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.votesystem.domain.dto.VotacaoAtaDTO;
import br.com.votesystem.domain.persistence.VotacaoAta;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Set;

public interface IVotoAtaService {
	
    VotacaoAta findById(Long id);

    Collection<VotacaoAta> findAll();

    Page<VotacaoAta> findAll(final Pageable pageable);

    @Transactional
    VotacaoAta add(VotacaoAta value) throws Exception;

    @Transactional
    VotacaoAta update(VotacaoAta value) throws Exception;

    @Transactional
    boolean remove(Long id) throws Exception;

    boolean validate(VotacaoAta value);

    <T> Set<String> validateEntity(T obj);

    boolean exists(Long id);

    String add(VotacaoAtaDTO value) throws Exception;

    String update(VotacaoAtaDTO value) throws Exception;

    String remove(VotacaoAtaDTO value) throws Exception;

    VotacaoAtaDTO convert(final VotacaoAta value);

    Collection<VotacaoAtaDTO> convert(final Collection<VotacaoAta> values);
}
