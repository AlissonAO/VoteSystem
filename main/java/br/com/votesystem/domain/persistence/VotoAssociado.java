package br.com.votesystem.domain.persistence;

/**
 * @author Alisson.Oliveira
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import br.com.votesystem.enuns.Voto;


@Entity
@Table(name="votacao_associado_voto", uniqueConstraints = @UniqueConstraint(name= "uc_associado_voto", columnNames = {"associado_id", "sessao_id"}))
public class VotoAssociado implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	private Long id = null;

	@NotNull(message = "Associado não pode ser nulo")
    @ManyToOne
    @JoinColumn(name="associado_id")
    private Associado associado = null;

    @NotNull(message = "Sessão pode ser nula")
    @ManyToOne
    @JoinColumn(name="sessao_id")
    private VotacaoSessao votacao = null;

    @NotNull(message = "Voto não pode ser nulo")
    @Enumerated(EnumType.ORDINAL)
    @Column(name="voto", nullable = false)
    private Voto voto = null;

    /**
     * Constructor
     */
    public VotoAssociado() {
        this.associado = null;
        this.votacao = null;
        this.voto = null;
    }

   /**
    * 
    * @param associate
    * @param voto
    */
    public VotoAssociado(Associado associado, Voto voto) {
        setAssociado(associado);
        setVoto(voto);;
    }

    /*
     * Setters and Getters
     */

    /**
     * Return associate
     * @return associate
     */
    public Associado getAssociado() {
        return associado;
    }

    /**
     * Set associate
     * @param associate
     */
    public void setAssociado(Associado associate) {
        this.associado = associate;
    }

    /**
     * Return poll
     * @return poll
     */
    public VotacaoSessao getPoll() {
        return votacao;
    }

    /**
     * Set poll
     * @param poll
     */
    public void setPoll(VotacaoSessao poll) {
        this.votacao = poll;
    }

    /**
     * Return voto
     * @return voto
     */
    public Voto getVoto() {
        return voto;
    }

    /**
     * Set voto
     * @param voto
     */
    public void setVoto(Voto voto) {
        this.voto = voto;
    }

    /**
     * Check if valid
     * @return valid
     */
    @Transient
    public boolean isValid() {
        return getAssociado() != null && getPoll() != null && getVoto() != null;
    }

    /**
     * @param other
     * @return same
     */
    public boolean same(final VotoAssociado other) {
        return getAssociado() != null;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VotacaoSessao getVotacao() {
		return votacao;
	}

	public void setVotacao(VotacaoSessao votacao) {
		this.votacao = votacao;
	}
    
    
}
