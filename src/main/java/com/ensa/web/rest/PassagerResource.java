package com.ensa.web.rest;

import com.ensa.domain.Passager;
import com.ensa.repository.PassagerRepository;
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
 * REST controller for managing {@link com.ensa.domain.Passager}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PassagerResource {

    private final Logger log = LoggerFactory.getLogger(PassagerResource.class);

    private static final String ENTITY_NAME = "accountApiPassager";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PassagerRepository passagerRepository;

    public PassagerResource(PassagerRepository passagerRepository) {
        this.passagerRepository = passagerRepository;
    }

    /**
     * {@code POST  /passagers} : Create a new passager.
     *
     * @param passager the passager to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new passager, or with status {@code 400 (Bad Request)} if the passager has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/passagers")
    public ResponseEntity<Passager> createPassager(@Valid @RequestBody Passager passager) throws URISyntaxException {
        log.debug("REST request to save Passager : {}", passager);
        if (passager.getId() != null) {
            throw new BadRequestAlertException("A new passager cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Passager result = passagerRepository.save(passager);
        return ResponseEntity
            .created(new URI("/api/passagers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /passagers/:id} : Updates an existing passager.
     *
     * @param id the id of the passager to save.
     * @param passager the passager to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated passager,
     * or with status {@code 400 (Bad Request)} if the passager is not valid,
     * or with status {@code 500 (Internal Server Error)} if the passager couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/passagers/{id}")
    public ResponseEntity<Passager> updatePassager(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Passager passager
    ) throws URISyntaxException {
        log.debug("REST request to update Passager : {}, {}", id, passager);
        if (passager.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, passager.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!passagerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Passager result = passagerRepository.save(passager);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, passager.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /passagers/:id} : Partial updates given fields of an existing passager, field will ignore if it is null
     *
     * @param id the id of the passager to save.
     * @param passager the passager to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated passager,
     * or with status {@code 400 (Bad Request)} if the passager is not valid,
     * or with status {@code 404 (Not Found)} if the passager is not found,
     * or with status {@code 500 (Internal Server Error)} if the passager couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/passagers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Passager> partialUpdatePassager(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Passager passager
    ) throws URISyntaxException {
        log.debug("REST request to partial update Passager partially : {}, {}", id, passager);
        if (passager.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, passager.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!passagerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Passager> result = passagerRepository
            .findById(passager.getId())
            .map(existingPassager -> {
                return existingPassager;
            })
            .map(passagerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, passager.getId().toString())
        );
    }

    /**
     * {@code GET  /passagers} : get all the passagers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of passagers in body.
     */
    @GetMapping("/passagers")
    public List<Passager> getAllPassagers() {
        log.debug("REST request to get all Passagers");
        return passagerRepository.findAll();
    }

    /**
     * {@code GET  /passagers/:id} : get the "id" passager.
     *
     * @param id the id of the passager to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the passager, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/passagers/{id}")
    public ResponseEntity<Passager> getPassager(@PathVariable Long id) {
        log.debug("REST request to get Passager : {}", id);
        Optional<Passager> passager = passagerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(passager);
    }

    /**
     * {@code DELETE  /passagers/:id} : delete the "id" passager.
     *
     * @param id the id of the passager to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/passagers/{id}")
    public ResponseEntity<Void> deletePassager(@PathVariable Long id) {
        log.debug("REST request to delete Passager : {}", id);
        passagerRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
