package com.ensa.repository;

import com.ensa.domain.Client;
import com.ensa.domain.Compte;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the Compte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {
    public Compte findByRib(String rib);
    public List<Compte> findByClient(Client client);
}
