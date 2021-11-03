package br.com.votesystem.domain.persistence;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Stream;

/**
 * @author Alisson.Oliveira
 */
import javax.annotation.PreDestroy;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import br.com.votesystem.enuns.Voto;

@Entity
@Table(name="votacao_sessao", uniqueConstraints = @UniqueConstraint(name="uc_votacao_sessao", columnNames = {"ata_id", "inicio_sessao"}))
public class VotacaoSessao implements Serializable, Comparable<VotacaoSessao> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	private Long id = null;
	
    @NotNull
    @Column(name="inicio_sessao", nullable = false)
    private ZonedDateTime beginAt = null;

    @Column(name="termino_sessao")
    private ZonedDateTime endAt = null;

    @NotNull(message = "Ata inválida")
    @OneToOne
    @JoinColumn(name="ata_id")
    private VotacaoAta votacaoAta = null;

    @OneToMany(mappedBy = "votacao", orphanRemoval = true, fetch = FetchType.EAGER)
    private Collection<VotoAssociado> votos = null;

    @Column(name="votos_sim")
    private Long votosSim = null;

    @Column(name="votos_nao")
    private Long votesNao = null;

    @Column(name="votos_total")
    private Long votosTotal = null;

    /**
     * Constructor
     */
    public VotacaoSessao() {
        this.beginAt = null;
        this.endAt = null;
        this.votacaoAta = null;
        this.votos = null;
        this.votosSim = null;
        this.votesNao = null;
        votosTotal = null;
    }

    /**
     * Destroy Event
     */
    @PreDestroy
    public void release() {
        if(votos != null) {
            this.votos.clear();
            this.votos = null;
        }
    }

    @PrePersist
    @PreUpdate
    public void updateVotos() {
        setVotosAgrees(countVotoSim());
        setVotosDisagrees(countVotoNao());
        setVotosTotal(countTotal());
    }

    @Override
    public int compareTo(final VotacaoSessao other) {
        int compare = -1;

        if(other != null) {
            int[] compares = {
                    getVotacaoAta() != null && other.getVotacaoAta() != null ? getVotacaoAta().getId().compareTo(other.getVotacaoAta().getId()) : 1,
                    getBeginAt() != null && other.getBeginAt() != null ? getBeginAt().compareTo(other.getBeginAt()) : 1
            };

            for(int c: compares) {
                compare = c;
                if(c != 0) {
                    break;
                }
            }
        }

        return compare;
    }

    /*
     * Setters and Getters
     */

    /**
     * Return beginAt
     * @return beginAt
     */
    public ZonedDateTime getBeginAt() {
        if(beginAt == null) {
            this.beginAt = ZonedDateTime.now();
        }

        return beginAt;
    }

    /**
     * Set beginAt
     * @param beginAt
     */
    public void setBeginAt(final ZonedDateTime beginAt) {
        this.beginAt = beginAt;
    }

    /**
     * Return endAt
     * @return endAt
     */
    public ZonedDateTime getEndAt() {
        return endAt;
    }

    /**
     * Set endAt
     * @param endAt
     */
    public void setEndAt(final ZonedDateTime endAt) {
        this.endAt = endAt;
    }

 
    public VotacaoAta getVotacaoAta() {
        return votacaoAta;
    }

    public void setVotacaoAta(VotacaoAta votacaoAta) {
        this.votacaoAta = votacaoAta;
    }

    /**
     * Return votes
     * @return votes
     */
    public Collection<VotoAssociado> getVotos() {
        if(votos == null) {
            this.votos = new HashSet<>();
        }
        return votos;
    }

    /**
     * Set votes
     * @param votes
     */
    public void setVotos(Collection<VotoAssociado> votos) {
        this.votos = votos;
    }

    /**
     * Return votesAgrees
     * @return votesAgrees
     */
    public Long getVotosAgrees() {
        if(votosSim == null) {
            this.votosSim = 0L;
        }

        return votosSim;
    }

    /**
     * Set votesAgrees
     * @param votesAgrees
     */
    public void setVotosAgrees(final Long votesAgrees) {
        this.votosSim = votesAgrees != null && votesAgrees >= 0L ? votesAgrees : 0L;
    }

    /**
     * Return votesDisagrees
     * @return votesDisagrees
     */
    public Long getVotosDisagrees() {
        if(votesNao == null) {
            this.votesNao = 0L;
        }

        return votesNao;
    }

    /**
     * Set votesDisagrees
     * @param votesDisagrees
     */
    public void setVotosDisagrees(final Long votesDisagrees) {
        this.votesNao = votesDisagrees != null && votesDisagrees >= 0L ? votesDisagrees : 0L;
    }

    /**
     * Return votesTotal
     * @return votesTotal
     */
    public Long getVotosTotal() {
        if(votosTotal == null) {
            this.votosTotal = 0L;
        }
        return votosTotal;
    }

    /**
     * Set votesTotal
     * @param votesTotal
     */
    public void setVotosTotal(final Long votesTotal) {
        this.votosTotal = votesTotal != null && votesTotal >= 0L ? votesTotal : 0L;
    }

    /**
     * Check if valid
     * @return valid
     */
    @Transient
    public boolean isValid() {
        return getVotacaoAta() != null && getVotacaoAta().isValid() && (getEndAt() == null || (getEndAt().isAfter(getBeginAt())));
    }

    @Transient
    public boolean isOpen() {
        ZonedDateTime now = ZonedDateTime.now();
        return isValid() && getEndAt() != null && !now.isBefore(getBeginAt()) && !now.isAfter(getEndAt());
    }

    /**
     * Stream of valid AssociateVotoPU
     * @return stream of votes
     */
    private Stream<VotoAssociado> validVotos() {
        return getVotos().stream().filter(p -> p != null && p.isValid() && p.getPoll().equals(this));
    }

    /**
     * Compute votes, if type is null return null
     * @param type
     * @return count
     */
    private Long computeVotos(final Voto type) {
        Long count = null;

        if(type != null) {
            count = validVotos().filter(p -> p.getVoto().equals(type)).count();
        }

        return count;
    }

    /**
     * Compute agrees
     * @return count
     */
    public Long countVotoSim() {
        return computeVotos(Voto.AGREE);
    }

    /**
     * Compute disagrees
     * @return count
     */
    public Long countVotoNao() {
        return computeVotos(Voto.DISAGREE);
    }

    /**
     * Compute total votes
     * @return total votes
     */
    public Long countTotal() {
        return validVotos().count();
    }

    /**
     * Check if already voted
     * @param value
     * @return voted
     */
    public boolean alreadyVotod(final Associado value) {
        return value != null && validVotos().filter(p -> p != null && p.getAssociado() != null && p.getAssociado().equals(value)).count() > 0;
    }

    /**
     * Add a vote
     * @param value
     * @return sucess
     */
    public boolean addVoto(VotoAssociado value) {
        boolean sucess = isOpen() && value != null && value.getAssociado() != null  && !alreadyVotod(value.getAssociado());

        if(sucess) {
            value.setPoll(this);
            getVotos().add(value);
        }

        return sucess;
    }

    /**
     * Add a vote
     * @param associate
     * @param voto
     * @return sucess
     */
    public boolean addVoto(final Associado associate, final Voto voto) {
        VotoAssociado obj = new VotoAssociado();

        obj.setAssociado(associate);
        obj.setVoto(voto);

        return addVoto(obj);
    }

    /**
     * Set end date
     * @param minutes
     */
    public void adjustDuration(final Long minutes) {
        setEndAt(getBeginAt().plusMinutes(minutes != null && minutes >0 ? minutes : 1L));
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
