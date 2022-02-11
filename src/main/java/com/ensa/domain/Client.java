package com.ensa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Kyc kyc;

    @JsonIgnoreProperties(value = { "client" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Compte compteClient;

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "client" }, allowSetters = true)
    private Set<Benificiaire> benificiaires = new HashSet<>();

    @ManyToOne
    private Agence agence;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Client id(Long id) {
        this.setId(id);
        return this;
    }

    public Set<Benificiaire> getBenificiaires() {
        return benificiaires;
    }

    public void setBenificiaires(Set<Benificiaire> benificiaires) {
        this.benificiaires = benificiaires;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Kyc getKyc() {
        return this.kyc;
    }

    public void setKyc(Kyc kyc) {
        this.kyc = kyc;
    }

    public Client kyc(Kyc kyc) {
        this.setKyc(kyc);
        return this;
    }

    public Agence getAgence() {
        return this.agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    public Client agence(Agence agence) {
        this.setAgence(agence);
        return this;
    }

    public Compte getCompteClient() {
        return compteClient;
    }

    public void setCompteClient(Compte compte) {
        this.compteClient = compte;
    }

// jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            "}";
    }
}
