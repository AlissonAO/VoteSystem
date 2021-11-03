package br.com.votesystem.domain.dto;

/**
 * @author Alisson.Oliveira
 */
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class VotacaoSessaoDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private String id = null;
	
	@JsonProperty("idVotoAta")
	private String idVotoAta = null;

	@JsonProperty("duracao")
	private Long duracao = null;

	/**
	 * Constructor
	 */
	public VotacaoSessaoDTO() {
		this.idVotoAta = null;
		this.duracao = null;
	}

	/*
	 * Setters and Getters
	 */

	/**
	 * Return idVotoAta 
	 *
	 * @return idVotoAta 
	 */
	public String getIdVotoAta () {
		return idVotoAta;
	}

	/**
	 * Set idVotoAta 
	 *
	 * @param idVotoAta 
	 */
	public void setIdVotoAta(String idVotoAta ) {
		this.idVotoAta = idVotoAta  != null && !idVotoAta .trim().isEmpty()
				? idVotoAta .trim().toLowerCase()
				: null;
	}

	/**
	 * Return duracao
	 *
	 * @return duracao
	 */
	public Long getDuracao() {
		return duracao;
	}

	/**
	 * Set duration
	 *
	 * @param duration
	 */
	public void setDuracao(Long duracao) {
		this.duracao = duracao;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
