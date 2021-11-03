package br.com.votesystem.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 
 * @author Alisson.Oliveira
 *
 */
public class VotacaoAtaDTO implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private Long id = null;
	
    @JsonProperty("descricao")
    private String descricao = null;

    @JsonProperty("resumo")
    private String resumo = null;

    @JsonProperty("votosSim")
    private Long votosSim = null;

    @JsonProperty("votosNao")
    private Long votosNao = null;

    @JsonProperty("votosTotal")
    private Long votosTotal = null;

    /**
     * Constructor
     */
    public VotacaoAtaDTO() {
        this.descricao = null;
        this.resumo = null;
        this.votosSim = null;
        this.votosNao = null;
        this.votosTotal = null;
    }

    /**
     * Constructor
     * @param id
     */
    public VotacaoAtaDTO( Long id) {
    	this.id = null;
        this.descricao = null;
        this.resumo = null;
        this.votosSim = null;
        this.votosNao = null;
        this.votosTotal = null;
    }

    /**
     * 
     * @param id
     * @param description
     * @param resume
     */
    public VotacaoAtaDTO(Long id, String description, String resume) {
    	this.id = null;
        setDescricao(description);
        setResumo(resume);
        this.votosSim = null;
        this.votosNao = null;
        this.votosTotal = null;
    }

   /**
    * 
    * @param id
    * @param votosSim
    * @param votosNao
    * @param votosTotal
    * @param descricao
    * @param resumo
    */
    public VotacaoAtaDTO(Long id, Long votosSim, Long votosNao, Long votosTotal, String descricao, String resumo) {
        setId(id);
        setDescricao(descricao);
        setResumo(resumo);
        setVotosSim(votosSim);
        setvotosNao(votosNao);
        setVotosTotal(votosTotal);
    }

    /**
     * Constructor
     * @param id
     * @param votosSim
     * @param votosNao
     * @param votosTotal
     */
    public VotacaoAtaDTO(Long id, Long votosSim, Long votosNao, Long votosTotal) {
    	setId(id);
    	setVotosSim(votosSim);
        setvotosNao(votosNao);
        setVotosTotal(votosTotal);
        setResumo(null);
        setDescricao(null);
    }

    /*
     * Setters and Getters
     */

   /**
    * 
    * @return
    */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Set Descricao
     * @param Descricao
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao != null && !descricao.trim().isEmpty() ? descricao.trim() : null;
    }

    /**
     * Return Resumo
     * @return Resumo
     */
    public String getResumo() {
        return resumo;
    }

    /**
     * Set resumo
     * @param resumo
     */
    public void setResumo(String resumo) {
        this.resumo = resumo != null && !resumo.trim().isEmpty() ? resumo.trim() : null;
    }

    /**
     * Return votosSim
     * @return votosSim
     */
    public Long getVotosSim() {
        if(votosSim == null) {
            this.votosSim = 0L;
        }
        return votosSim;
    }

    /**
     * Set votosSim
     * @param votosSim
     */
    public void setVotosSim(Long votosSim) {
        this.votosSim = votosSim != null && votosSim >= 0 ? votosSim : null;
    }

  /**
   * 
   * @return
   */
    
    public Long getVotosNao() {
        if(votosNao == null) {
            this.votosNao = 0L;
        }
        return votosNao;
    }

   /**
    * 
    * @param votosNao
    */
    public void setvotosNao(Long countDisagree) {
        this.votosNao = countDisagree != null && countDisagree >= 0 ? countDisagree : null;
    }

    /**
     * Return votosTotal
     * @return votosTotal
     */
    public Long getVotosTotal() {
        if(votosTotal == null) {
            this.votosTotal = getVotosSim() + getVotosNao();
        }
        return votosTotal;
    }

    /**
     * Set votosTotal
     * @param votosTotal
     */
    public void setVotosTotal(Long votosTotal) {
        this.votosTotal = votosTotal != null && votosTotal >= 0 ? votosTotal : null;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
