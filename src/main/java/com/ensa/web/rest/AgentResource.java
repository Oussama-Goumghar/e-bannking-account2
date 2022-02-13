package com.ensa.web.rest;

import com.ensa.domain.Agence;
import com.ensa.domain.Agent;
import com.ensa.service.AgentService;
import com.ensa.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

/**
 * REST controller for managing {@link com.ensa.domain.Agent}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AgentResource {

    private final Logger log = LoggerFactory.getLogger(AgentResource.class);

    private static final String ENTITY_NAME = "accountApiAgent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private AgentService agentService;



    @PostMapping("/agents/referece-agence/{referenceAgence}")
    public int createAgent(@Valid @RequestBody Agent agent,@PathVariable String referenceAgence) throws URISyntaxException {
        log.debug("REST request to save Agent : {}", agent);
        if (agent.getId() != null) {
            throw new BadRequestAlertException("A new agent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return agentService.saveAgent(agent,referenceAgence);

    }

    @PutMapping("/agents/{login}")
    public ResponseEntity<Agent> updateAgent(@PathVariable String login, @Valid @RequestBody Agent agent)
        throws URISyntaxException {

        Agent result = agentService.updateAgent(agent,login);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agent.getId().toString()))
            .body(result);
    }


    @PatchMapping(value = "/agents/{login}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity partialUpdateAgent(@PathVariable String login,
                                             @NotNull @RequestBody Agent agent
    ) throws URISyntaxException {

        Agent result = agentService.updateAgentPartial(agent,login);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agent.getId().toString()))
            .body(result);
    }


    @GetMapping("/agents")
    public List<Agent> getAllAgents() {
        log.debug("REST request to get all Agents");
        return agentService.getAllAgents();
    }


    @GetMapping("/agents/{id}")
    public Agent getAgent(@PathVariable Long id) {
        log.debug("REST request to get Agent : {}", id);

        return agentService.getById(id);
    }

    @GetMapping("/agents/{refAg}")
    public List<Agent> getAgentByAgence(@PathVariable String refAg) {
        log.debug("REST request to get Agent : {}", refAg);

        return agentService.getByAgence(refAg);
    }


    @DeleteMapping("/agents/{login}")
    public void deleteAgent(@PathVariable String login) {
        log.debug("REST request to delete Agent : {}", login);
        agentService.deleteAgent(login);

    }

    @GetMapping("/agents/credit-account/{login}/montant/{montant}")
    public int creditAccountAgent(@PathVariable String login, @PathVariable double montant) {
       return agentService.creditCompteAgent(login, montant);
    }

    @GetMapping("/agents/debit-account/{login}/montant/{montant}")
    public int debitAccountAgent(@PathVariable String login, @PathVariable double montant) {
        return agentService.debiteCompteAgent(login, montant);
    }


}
