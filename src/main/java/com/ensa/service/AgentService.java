package com.ensa.service;


import com.ensa.domain.Agence;
import com.ensa.domain.Agent;
import com.ensa.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    public Agent saveAgent(Agent agent){return agentRepository.save(agent);}

    public Agent updateAgent(Agent agent){
        Agent agent1 = agentRepository.findById(agent.getId()).orElseThrow();

        agent1.setLogin(agent.getLogin());
        agent1.setNom(agent.getNom());
        agent1.setPrenom(agent.getPrenom());
        agent1.setPassword(agent.getPassword());
        agent1.setCompteAgent(agent.getCompteAgent());
        agent1.setAgence(agent.getAgence());

        return agentRepository.save(agent1);
    }

    public Agent updateAgentPartial(Agent agent)
    {
        Agent agent1 = agentRepository.findById(agent.getId()).orElseThrow();


        if(agent.getNom() != null){agent1.setNom(agent.getNom());}
        if(agent.getPrenom() != null){agent1.setPrenom(agent.getPrenom());}
        if(agent.getLogin() != null){agent1.setLogin(agent.getLogin());}
        if(agent.getPassword() != null){agent1.setPassword(agent.getPassword());}
        if(agent.getCompteAgent() != null){agent1.setCompteAgent(agent.getCompteAgent());}
        if(agent.getAgence() != null){agent1.setAgence(agent.getAgence());}

        return agentRepository.save(agent1);
    }

    public List<Agent> getAllAgents(){
        return agentRepository.findAll();
    }

    public List<Agent> getByAgence(Agence agence){
        return agentRepository.findByAgence(agence);
    }

    public Agent getById(Long id)
    {
        Agent agent = agentRepository.findById(id).orElseThrow();
        return agent;
    }

    public ResponseEntity<Void> deleteAgent(Long id)
    {
        agentRepository.deleteById(id);
        return null;
    }


    public boolean existsById(Long id) {
        return agentRepository.existsById(id);
    }

}
