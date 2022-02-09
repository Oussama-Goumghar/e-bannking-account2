package com.ensa.repository;

import com.ensa.domain.Agence;
import com.ensa.domain.Gab;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Gab entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GabRepository extends JpaRepository<Gab, Long> {
    public Gab findByAgence(Agence agence);
}
