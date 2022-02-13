package com.ensa.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Agence.
 */
@Entity
@Table(name = "agence")
public class Agence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "ville")
    private String ville;

    @Column(name = "reference")
    private String reference;

    @Column(name = "plafond_montant")
    private Double plafondMontant;

    @Column(name = "plafond_transaction")
    private Integer plafondTransaction;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Agence id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return this.address;
    }

    public Agence address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVille() {
        return this.ville;
    }

    public Agence ville(String ville) {
        this.setVille(ville);
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getReference() {
        return this.reference;
    }

    public Agence reference(String reference) {
        this.setReference(reference);
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Double getPlafondMontant() {
        return this.plafondMontant;
    }

    public Agence plafondMontant(Double plafondMontant) {
        this.setPlafondMontant(plafondMontant);
        return this;
    }

    public void setPlafondMontant(Double plafondMontant) {
        this.plafondMontant = plafondMontant;
    }

    public Integer getPlafondTransaction() {
        return this.plafondTransaction;
    }

    public Agence plafondTransaction(Integer plafondTransaction) {
        this.setPlafondTransaction(plafondTransaction);
        return this;
    }

    public void setPlafondTransaction(Integer plafondTransaction) {
        this.plafondTransaction = plafondTransaction;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agence)) {
            return false;
        }
        return id != null && id.equals(((Agence) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Agence{" +
            "id=" + getId() +
            ", address='" + getAddress() + "'" +
            ", ville='" + getVille() + "'" +
            ", reference='" + getReference() + "'" +
            ", plafondMontant=" + getPlafondMontant() +
            ", plafondTransaction=" + getPlafondTransaction() +
            "}";
    }
}
