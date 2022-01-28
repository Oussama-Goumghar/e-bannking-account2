package com.ensa.repository;

import com.ensa.domain.Benificiaire;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Benificiaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BenificiaireRepository extends JpaRepository<Benificiaire, Long> {}
