package br.com.votesystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.votesystem.domain.persistence.VotacaoAta;

@Repository
public interface IVotoAtaRepository extends JpaRepository<VotacaoAta, String> {
	
	 VotacaoAta findById(Long id);
	
}
