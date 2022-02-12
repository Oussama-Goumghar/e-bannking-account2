package com.ensa.repository;

import com.ensa.domain.Agence;
import com.ensa.domain.Agent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the Agent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    public List<Agent> findByAgence(Agence agence);

    Agent findByLogin(String login);

    boolean existsByLogin(String login);
}
