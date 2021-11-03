package br.com.votesystem.domain.dto;

/**
 * @author Alisson.Oliveira
 */
import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AssociadoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private Long id = null;

	@NotBlank(message = "CPF obrigatório")
	@NotNull(message = "CPF não pode ser nulo")
	@JsonProperty("cpf")
	private String cpf = null;

	/**
	 * Constructor
	 */

	public AssociadoDTO() {
		
	}
	/**
	 * Constructor
	 * 
	 * @param Cpf
	 */
	public AssociadoDTO(Long id) {
		setId(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param Cpf
	 */
	public AssociadoDTO(Long id, final String Cpf) {
		setId(id);
		setCpf(Cpf);
	}

	/*
	 * Setters and Getters
	 */

	/**
	 * Return Cpf
	 * 
	 * @return Cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * Set Cpf
	 * 
	 * @param Cpf
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}