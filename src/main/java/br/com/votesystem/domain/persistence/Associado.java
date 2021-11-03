package br.com.votesystem.domain.persistence;

import br.com.votesystem.util.StringUtil;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "votacao_associado", uniqueConstraints = @UniqueConstraint(name="uc_associado", columnNames = {"cpf"}))
public class Associado implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	private Long id = null;
	
    @NotNull
    @Size(max = 11, message = "Utrapassou o tamanho maximo")
    @Column(name = "cpf", length = 11, nullable = true)
    private String cpf = null;

    /**
     * Constructor
     */
    public Associado() {
        this.cpf = null;
    }

    /**
     * Constructor
     *
     * @param cpf
     */
    public Associado(final String cpf) {
        setCpf(cpf);
    }

    /**
     * Constructor
     * @param id
     * @param cpf
     */
    public Associado(Long id, String cpf) {
    	setId(id);
        setCpf(cpf);
    }


    /**
     * Return cpf
     *
     * @return cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Set cpf
     *
     * @param cpf
     */
    public void setCpf(final String cpf) {
        this.cpf = cpf != null && !cpf.trim().isEmpty() ? StringUtil.clean(cpf) : null;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


}
