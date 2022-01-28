package com.ensa.repository;

import com.ensa.domain.Passager;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Passager entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PassagerRepository extends JpaRepository<Passager, Long> {}
