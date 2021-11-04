package br.com.votesystem.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.votesystem.domain.persistence.VotacaoSessao;

@Repository
public interface IVotSessaoRepository extends JpaRepository<VotacaoSessao, Long> {
	
	Optional<VotacaoSessao> findById(Long id);
}
