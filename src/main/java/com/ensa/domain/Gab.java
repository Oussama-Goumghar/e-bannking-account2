package com.ensa.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Gab.
 */
@Entity
@Table(name = "gab")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Gab implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "fond")
    private Double fond;

    @Column(name = "address")
    private String address;

    @OneToOne
    @JoinColumn(unique = true)
    private Agence agence;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Gab id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFond() {
        return this.fond;
    }

    public Gab fond(Double fond) {
        this.setFond(fond);
        return this;
    }

    public void setFond(Double fond) {
        this.fond = fond;
    }

    public String getAddress() {
        return this.address;
    }

    public Gab address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Agence getAgence() {
        return this.agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    public Gab agence(Agence agence) {
        this.setAgence(agence);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gab)) {
            return false;
        }
        return id != null && id.equals(((Gab) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gab{" +
            "id=" + getId() +
            ", fond=" + getFond() +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
