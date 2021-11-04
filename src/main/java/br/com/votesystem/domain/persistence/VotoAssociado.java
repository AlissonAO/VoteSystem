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
    private VotacaoSessao votacaoSessao = null;

    @NotNull(message = "Voto não pode ser nulo")
    @Enumerated(EnumType.ORDINAL)
    @Column(name="voto", nullable = false)
    private Voto voto = null;

    /**
     * Constructor
     */
    public VotoAssociado() {
        this.associado = null;
        this.votacaoSessao = null;
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
     * Check if valid
     * @return valid
     */
    @Transient
    public boolean isValid() {
        return getAssociado() != null;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Associado getAssociado() {
		return associado;
	}

	public void setAssociado(Associado associado) {
		this.associado = associado;
	}

	public VotacaoSessao getVotacaoSessao() {
		return votacaoSessao;
	}

	public void setVotacaoSessao(VotacaoSessao votacaoSessao) {
		this.votacaoSessao = votacaoSessao;
	}

	public Voto getVoto() {
		return voto;
	}

	public void setVoto(Voto voto) {
		this.voto = voto;
	}


    
    
}
