package com.ensa.repository;

import com.ensa.domain.Agence;
import com.ensa.domain.Client;
import com.ensa.domain.Kyc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the Client entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByKyc(Kyc kyc);

    List<Client> findByAgence(Agence agence);

    Client findClientByKyc_NumIdentite(String numIdentite);

}
