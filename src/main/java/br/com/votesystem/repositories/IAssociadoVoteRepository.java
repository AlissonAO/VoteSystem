package br.com.votesystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.votesystem.domain.persistence.VotoAssociado;

@Repository
public interface IAssociadoVoteRepository extends JpaRepository<VotoAssociado, String> {
	VotoAssociado findById(Long id);
}
