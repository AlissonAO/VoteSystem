package br.com.votesystem.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.votesystem.domain.persistence.Associado;

@Repository
public interface IAssociadoRepository extends JpaRepository<Associado, String> {

    Collection<Associado> findByCpf(String cpf);

    Associado findById(Long id);
}
