package br.com.votesystem.services.interfaces;

import javax.transaction.Transactional;

import br.com.votesystem.domain.dto.VotacaoSessaoDTO;
import br.com.votesystem.domain.persistence.VotacaoSessao;

import java.util.Set;

public interface IVotoSessaoService {
    VotacaoSessao findById(String id);

    boolean exists(String id);

    boolean validate(VotacaoSessao value);

    <T> Set<String> validateEntity(T obj);

    @Transactional
    VotacaoSessao add(VotacaoSessao value) throws Exception;

    String add(VotacaoSessaoDTO value) throws Exception;

    VotacaoSessao convert(VotacaoSessaoDTO value);
}
