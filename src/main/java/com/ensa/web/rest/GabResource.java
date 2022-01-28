package com.ensa.web.rest;

import com.ensa.domain.Gab;
import com.ensa.repository.GabRepository;
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
 * REST controller for managing {@link com.ensa.domain.Gab}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GabResource {

    private final Logger log = LoggerFactory.getLogger(GabResource.class);

    private static final String ENTITY_NAME = "accountApiGab";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GabRepository gabRepository;

    public GabResource(GabRepository gabRepository) {
        this.gabRepository = gabRepository;
    }

    /**
     * {@code POST  /gabs} : Create a new gab.
     *
     * @param gab the gab to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gab, or with status {@code 400 (Bad Request)} if the gab has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gabs")
    public ResponseEntity<Gab> createGab(@Valid @RequestBody Gab gab) throws URISyntaxException {
        log.debug("REST request to save Gab : {}", gab);
        if (gab.getId() != null) {
            throw new BadRequestAlertException("A new gab cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Gab result = gabRepository.save(gab);
        return ResponseEntity
            .created(new URI("/api/gabs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gabs/:id} : Updates an existing gab.
     *
     * @param id the id of the gab to save.
     * @param gab the gab to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gab,
     * or with status {@code 400 (Bad Request)} if the gab is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gab couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gabs/{id}")
    public ResponseEntity<Gab> updateGab(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Gab gab)
        throws URISyntaxException {
        log.debug("REST request to update Gab : {}, {}", id, gab);
        if (gab.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gab.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gabRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Gab result = gabRepository.save(gab);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gab.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /gabs/:id} : Partial updates given fields of an existing gab, field will ignore if it is null
     *
     * @param id the id of the gab to save.
     * @param gab the gab to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gab,
     * or with status {@code 400 (Bad Request)} if the gab is not valid,
     * or with status {@code 404 (Not Found)} if the gab is not found,
     * or with status {@code 500 (Internal Server Error)} if the gab couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/gabs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Gab> partialUpdateGab(@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody Gab gab)
        throws URISyntaxException {
        log.debug("REST request to partial update Gab partially : {}, {}", id, gab);
        if (gab.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gab.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gabRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Gab> result = gabRepository
            .findById(gab.getId())
            .map(existingGab -> {
                if (gab.getFond() != null) {
                    existingGab.setFond(gab.getFond());
                }
                if (gab.getAddress() != null) {
                    existingGab.setAddress(gab.getAddress());
                }

                return existingGab;
            })
            .map(gabRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gab.getId().toString())
        );
    }

    /**
     * {@code GET  /gabs} : get all the gabs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gabs in body.
     */
    @GetMapping("/gabs")
    public List<Gab> getAllGabs() {
        log.debug("REST request to get all Gabs");
        return gabRepository.findAll();
    }

    /**
     * {@code GET  /gabs/:id} : get the "id" gab.
     *
     * @param id the id of the gab to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gab, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gabs/{id}")
    public ResponseEntity<Gab> getGab(@PathVariable Long id) {
        log.debug("REST request to get Gab : {}", id);
        Optional<Gab> gab = gabRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gab);
    }

    /**
     * {@code DELETE  /gabs/:id} : delete the "id" gab.
     *
     * @param id the id of the gab to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gabs/{id}")
    public ResponseEntity<Void> deleteGab(@PathVariable Long id) {
        log.debug("REST request to delete Gab : {}", id);
        gabRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
