package com.ensa.repository;

import com.ensa.domain.Benificiaire;
import com.ensa.domain.Kyc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the Benificiaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BenificiaireRepository extends JpaRepository<Benificiaire, Long> {
    Benificiaire findByKyc(Kyc kyc);

    List<Benificiaire> findBenificiairesByClient_Kyc_NumIdentite(String numIdentityClient);

    Optional<Benificiaire> findBenificiaireByKyc_NomAndClient_Kyc_NumIdentite(String nomBenificiair, String numIdentiteClient);

    Optional<Benificiaire> findBenificiaireByKyc_Nom(String nomBenificiair);

    Optional<Benificiaire> findBenificiaireById(Long id);
}
