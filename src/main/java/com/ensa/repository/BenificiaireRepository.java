package com.ensa.repository;

import com.ensa.domain.Benificiaire;
import com.ensa.domain.Kyc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Benificiaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BenificiaireRepository extends JpaRepository<Benificiaire, Long> {
    public Benificiaire findByKyc(Kyc kyc);
}
