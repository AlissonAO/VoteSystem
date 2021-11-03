package br.com.votesystem.domain.persistence;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="votacao_ata")
public class VotacaoAta implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	private Long id = null;
	
	
    @Column(name="descricao", length = 50)
    private String descricao = null;

    @Column(name="resumo", length = 255)
    private String resumo = null;

    @OneToOne(mappedBy = "votacaoAta", orphanRemoval = true, fetch = FetchType.EAGER)
    private VotacaoSessao votacaoSessao = null;

    /**
     * Constructor
     */
    public VotacaoAta() {
        this.descricao = null;
        this.resumo = null;
        this.votacaoSessao = null;
    }

    /**
     * Constructor
     * @param descricao
     * @param resumo
     */
    public VotacaoAta(String descricao, final String resumo) {
        setDescricao(descricao);
        setResumo(resumo);
        this.votacaoSessao = null;
    }

    /**
     * Constructor
     * @param id
     * @param description
     * @param resume
     */
    public VotacaoAta(Long id, String descricao, String resumo) {
        setId(id);
        setDescricao(descricao);
        setResumo(resumo);
        this.votacaoSessao = null;
    }


    /*
     * Setters and Getters
     */


    public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * 
	 * @return
	 */
    public String getResumo() {
		return resumo;
	}

    /**
     * 
     * @param resumo
     */
	public void setResumo(String resumo) {
		this.resumo = resumo;
	}

	/**
     * Return poll
     * @return poll
     */
    public VotacaoSessao getPoll() {
        return votacaoSessao;
    }

    /**
     * Set poll
     * @param poll
     */
    public void setPoll(VotacaoSessao poll) {
        this.votacaoSessao = poll;
    }

   /**
    * 
    * @return
    */
    @Transient
    public boolean isValid() {
        return getId() != null && getDescricao() != null;
    }

    /**
     * Create a poll
     * @param durationMinutes
     * @return poll
     */
    public VotacaoSessao createPoll(final Long durationMinutes) {
        VotacaoSessao obj = new VotacaoSessao();

        obj.setVotacaoAta(this);
        obj.setEndAt(obj.getBeginAt().plusMinutes(durationMinutes != null ? durationMinutes : 1L));
        setPoll(obj);
        return obj;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
