package com.ensa.service;


import com.ensa.domain.Agence;
import com.ensa.domain.Agent;
import com.ensa.repository.AgenceRepository;
import com.ensa.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private AgenceRepository agenceRepository;

    public Agent saveAgent(Agent agent)
    {
        if(agentRepository.existsByLogin(agent.getLogin()))
        {
            return null;
        }
        else
        {
            return agentRepository.save(agent);
        }
    }


    public Agent updateAgent(Agent agent,String login){
        if(login == agent.getLogin())
        {
            Agent agent1 = agentRepository.findById(agent.getId()).orElseThrow();

            agent1.setLogin(agent.getLogin());
            agent1.setNom(agent.getNom());
            agent1.setPrenom(agent.getPrenom());
            agent1.setPassword(agent.getPassword());
            agent1.setCompteAgent(agent.getCompteAgent());
            agent1.setAgence(agent.getAgence());

            return agentRepository.save(agent1);
        }
        else return null;
    }

    public Agent updateAgentPartial(Agent agent,String login)
    {
        if(login == agent.getLogin()) {
            Agent agent1 = agentRepository.findById(agent.getId()).orElseThrow();


            if (agent.getNom() != null) {
                agent1.setNom(agent.getNom());
            }
            if (agent.getPrenom() != null) {
                agent1.setPrenom(agent.getPrenom());
            }
            if (agent.getLogin() != null) {
                agent1.setLogin(agent.getLogin());
            }
            if (agent.getPassword() != null) {
                agent1.setPassword(agent.getPassword());
            }
            if (agent.getCompteAgent() != null) {
                agent1.setCompteAgent(agent.getCompteAgent());
            }
            if (agent.getAgence() != null) {
                agent1.setAgence(agent.getAgence());
            }

            return agentRepository.save(agent1);
        }
        else return null ;
    }

    public List<Agent> getAllAgents(){
        return agentRepository.findAll();
    }

    public List<Agent> getByAgence(String refAgence){
        Agence agence = agenceRepository.findByReference(refAgence);
        return agentRepository.findByAgence(agence);
    }

    public Agent getById(Long id)
    {
        Agent agent = agentRepository.findById(id).orElseThrow();
        return agent;
    }

    public void deleteAgent(String login)
    {
        Agent agent = agentRepository.findByLogin(login);
        agentRepository.delete(agent);
    }


    public boolean existsById(Long id) {
        return agentRepository.existsById(id);
    }

}
