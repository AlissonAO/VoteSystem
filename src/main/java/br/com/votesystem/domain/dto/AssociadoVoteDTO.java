package br.com.votesystem.domain.dto;

/**
 * @author Alisson.Oliveira
 */
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.votesystem.enuns.Voto;

public class AssociadoVoteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private Long id = null;

	@JsonProperty("votoAtaId")
	private Long votoAtaId = null;

	@JsonProperty("associadoId")
	private Long associadoId = null;

	@JsonProperty("voto")
	private Voto voto = null;

	/**
	 * Constructor
	 */
	public AssociadoVoteDTO() {
		this.associadoId = null;
		this.votoAtaId = null;
		this.voto = null;
	}

	
	/*
	 * Setters and Getters
	 */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVotoAtaId() {
		return votoAtaId;
	}

	public void setVotoAtaId(Long votoAtaId) {
		this.votoAtaId = votoAtaId;
	}

	public Long getAssociadoId() {
		return associadoId;
	}

	public void setAssociadoId(Long associadoId) {
		this.associadoId = associadoId;
	}

	public Voto getVoto() {
		return voto;
	}

	public void setVoto(Voto voto) {
		this.voto = voto;
	}




}
