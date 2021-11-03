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
	private String id = null;

	@JsonProperty("minuteMeetingId")
	private String minuteMeetingId = null;

	@JsonProperty("associateId")
	private String associateId = null;

	@JsonProperty("vote")
	private Voto voto = null;

	/**
	 * Constructor
	 */
	public AssociadoVoteDTO() {
		super();
		this.minuteMeetingId = null;
		this.associateId = null;
		this.voto = null;
	}

	/*
	 * Setters and Getters
	 */

	/**
	 * Return minuteMeetingId
	 *
	 * @return minuteMeetingId
	 */
	public String getMinuteMeetingId() {
		return minuteMeetingId;
	}

	/**
	 * Set minuteMeetingId
	 *
	 * @param minuteMeetingId
	 */
	public void setMinuteMeetingId(final String minuteMeetingId) {
		this.minuteMeetingId = minuteMeetingId != null && !minuteMeetingId.trim().isEmpty()
				? minuteMeetingId.trim().toLowerCase()
				: null;
	}

	/**
	 * Return associateId
	 *
	 * @return associateId
	 */
	public String getAssociateId() {
		return associateId;
	}

	/**
	 * Set associateId
	 *
	 * @param associateId
	 */
	public void setAssociateId(final String associateId) {
		this.associateId = associateId != null && !associateId.trim().isEmpty() ? associateId.trim().toLowerCase()
				: null;
	}

	/**
	 * Return vote
	 *
	 * @return vote
	 */
	public Voto getVote() {
		return voto;
	}

	/**
	 * Set vote
	 *
	 * @param voto
	 */
	public void setVote(final Voto voto) {
		this.voto = voto;
	}
	
    /**
     * Constructor
     * @param id2 
     */

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
