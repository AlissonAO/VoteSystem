package br.com.votesystem.domain.persistence;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ComunId implements Serializable {
    @Id
    private String id = null;

    /**
     * Constructor
     */
    public ComunId() {
        this.id = null;
    }

    /**
     * Constructor
     * @param id
     */
    public ComunId(String id) {
        setId(id);
    }

    /**
     * Override equals
     * @param other
     * @return equal
     */
    @Override
    public boolean equals(final Object other) {
        return other != null && other instanceof ComunId && getId().equals(((ComunId) other).getId());
    }

    /**
     * Override hashCode
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    /**
     * Override toString
     * @return id in json format
     */
    @Override
    public String toString() {
        return String.format("{ \"id\": \"%s\"}", getId());
    }

    /**
     * Return id
     * @return id
     */
    public String getId() {
        if(id == null || id.trim().isEmpty()) {
            this.id = UUID.randomUUID().toString().replace("-", "");
        }

        return id;
    }

    /**
     * Set id
     * @param id
     */
    public void setId(String id) {
        this.id = id != null ? id.trim().toLowerCase() : null;
    }
}
