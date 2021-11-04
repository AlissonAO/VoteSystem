package br.com.votesystem.services.interfaces;

import javax.transaction.Transactional;

import br.com.votesystem.domain.dto.AssociadoVoteDTO;
import br.com.votesystem.domain.persistence.VotoAssociado;

import java.util.Set;

public interface IAssociadoVotoService {
	
    VotoAssociado findById(Long id);

    boolean exists(Long id);

    boolean validate(VotoAssociado value);

    <T> Set<String> validateEntity(T obj);

    @Transactional
    VotoAssociado add(VotoAssociado value);

    String add(AssociadoVoteDTO value) throws Exception;

    VotoAssociado convert(final AssociadoVoteDTO value);
}
