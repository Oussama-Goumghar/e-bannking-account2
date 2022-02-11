package com.ensa.repository;

import com.ensa.domain.Kyc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Kyc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KycRepository extends JpaRepository<Kyc, Long> {
    public Kyc findByNumIdentite(String numIdentite);
}
