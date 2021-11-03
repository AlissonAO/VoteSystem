package br.com.votesystem.enuns;

/**
 * @author Alisson.Oliveira
 */

import com.fasterxml.jackson.annotation.JsonValue;

public enum Voto {
    AGREE(true, "Sim"), DISAGREE(false, "Não");

    private Boolean concordo = null;
    private String descricao = null;

    Voto(boolean concordo, String descricao) {
        setConcordo(concordo);
        setDescricao(descricao);
    }

    /**
     * Return concordo (true or false)
     * @return concordo
     */
    public Boolean getConcordo() {
        return concordo;
    }

    /**
     * Set concordo
     * @param concordo
     */
    public void setConcordo(Boolean concordo) {
        this.concordo = concordo;
    }

    /**
     * Return descricao
     * @return descricao
     */
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    /**
     * Set descricao
     * @param descricao
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
