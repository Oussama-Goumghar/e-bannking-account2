package com.ensa.web.rest;

import com.ensa.domain.Agent;
import com.ensa.repository.AgentRepository;
import com.ensa.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

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

    private final AgentRepository agentRepository;

    public AgentResource(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    /**
     * {@code POST  /agents} : Create a new agent.
     *
     * @param agent the agent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agent, or with status {@code 400 (Bad Request)} if the agent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/agents")
    public ResponseEntity<Agent> createAgent(@Valid @RequestBody Agent agent) throws URISyntaxException {
        log.debug("REST request to save Agent : {}", agent);
        if (agent.getId() != null) {
            throw new BadRequestAlertException("A new agent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Agent result = agentRepository.save(agent);
        return ResponseEntity
            .created(new URI("/api/agents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /agents/:id} : Updates an existing agent.
     *
     * @param id the id of the agent to save.
     * @param agent the agent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agent,
     * or with status {@code 400 (Bad Request)} if the agent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/agents/{id}")
    public ResponseEntity<Agent> updateAgent(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Agent agent)
        throws URISyntaxException {
        log.debug("REST request to update Agent : {}, {}", id, agent);
        if (agent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Agent result = agentRepository.save(agent);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agent.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /agents/:id} : Partial updates given fields of an existing agent, field will ignore if it is null
     *
     * @param id the id of the agent to save.
     * @param agent the agent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agent,
     * or with status {@code 400 (Bad Request)} if the agent is not valid,
     * or with status {@code 404 (Not Found)} if the agent is not found,
     * or with status {@code 500 (Internal Server Error)} if the agent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/agents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Agent> partialUpdateAgent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Agent agent
    ) throws URISyntaxException {
        log.debug("REST request to partial update Agent partially : {}, {}", id, agent);
        if (agent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Agent> result = agentRepository
            .findById(agent.getId())
            .map(existingAgent -> {
                if (agent.getNom() != null) {
                    existingAgent.setNom(agent.getNom());
                }
                if (agent.getPrenom() != null) {
                    existingAgent.setPrenom(agent.getPrenom());
                }
                if (agent.getLogin() != null) {
                    existingAgent.setLogin(agent.getLogin());
                }
                if (agent.getPassword() != null) {
                    existingAgent.setPassword(agent.getPassword());
                }

                return existingAgent;
            })
            .map(agentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agent.getId().toString())
        );
    }

    /**
     * {@code GET  /agents} : get all the agents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agents in body.
     */
    @GetMapping("/agents")
    public List<Agent> getAllAgents() {
        log.debug("REST request to get all Agents");
        return agentRepository.findAll();
    }

    /**
     * {@code GET  /agents/:id} : get the "id" agent.
     *
     * @param id the id of the agent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/agents/{id}")
    public ResponseEntity<Agent> getAgent(@PathVariable Long id) {
        log.debug("REST request to get Agent : {}", id);
        Optional<Agent> agent = agentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(agent);
    }

    /**
     * {@code DELETE  /agents/:id} : delete the "id" agent.
     *
     * @param id the id of the agent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/agents/{id}")
    public ResponseEntity<Void> deleteAgent(@PathVariable Long id) {
        log.debug("REST request to delete Agent : {}", id);
        agentRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
