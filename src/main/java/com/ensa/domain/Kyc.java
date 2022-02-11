package com.ensa.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Kyc.
 */
@Entity
@Table(name = "kyc")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Kyc implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "titre")
    private String titre;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "type_identite")
    private String typeIdentite;

    @Column(name = "num_identite")
    private String numIdentite;

    @Column(name = "validate_time_identite")
    private LocalDate validateTimeIdentite;

    @Column(name = "profession")
    private String profession;

    @Column(name = "nationalite")
    private String nationalite;

    @Column(name = "address")
    private String address;

    @Column(name = "gsm")
    private String gsm;

    @Column(name = "email")
    private String email;


    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Kyc id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return this.titre;
    }

    public Kyc titre(String titre) {
        this.setTitre(titre);
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getNom() {
        return this.nom;
    }

    public Kyc nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Kyc prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTypeIdentite() {
        return this.typeIdentite;
    }

    public Kyc typeIdentite(String typeIdentite) {
        this.setTypeIdentite(typeIdentite);
        return this;
    }

    public void setTypeIdentite(String typeIdentite) {
        this.typeIdentite = typeIdentite;
    }

    public String getNumIdentite() {
        return this.numIdentite;
    }

    public Kyc numIdentite(String numIdentite) {
        this.setNumIdentite(numIdentite);
        return this;
    }

    public void setNumIdentite(String numIdentite) {
        this.numIdentite = numIdentite;
    }

    public LocalDate getValidateTimeIdentite() {
        return this.validateTimeIdentite;
    }

    public Kyc validateTimeIdentite(LocalDate validateTimeIdentite) {
        this.setValidateTimeIdentite(validateTimeIdentite);
        return this;
    }

    public void setValidateTimeIdentite(LocalDate validateTimeIdentite) {
        this.validateTimeIdentite = validateTimeIdentite;
    }

    public String getProfession() {
        return this.profession;
    }

    public Kyc profession(String profession) {
        this.setProfession(profession);
        return this;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getNationalite() {
        return this.nationalite;
    }

    public Kyc nationalite(String nationalite) {
        this.setNationalite(nationalite);
        return this;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getAddress() {
        return this.address;
    }

    public Kyc address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGsm() {
        return this.gsm;
    }

    public Kyc gsm(String gsm) {
        this.setGsm(gsm);
        return this;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public String getEmail() {
        return this.email;
    }

    public Kyc email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kyc)) {
            return false;
        }
        return id != null && id.equals(((Kyc) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Kyc{" +
            "id=" + getId() +
            ", titre='" + getTitre() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", typeIdentite='" + getTypeIdentite() + "'" +
            ", numIdentite='" + getNumIdentite() + "'" +
            ", validateTimeIdentite='" + getValidateTimeIdentite() + "'" +
            ", profession='" + getProfession() + "'" +
            ", nationalite='" + getNationalite() + "'" +
            ", address='" + getAddress() + "'" +
            ", gsm='" + getGsm() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
