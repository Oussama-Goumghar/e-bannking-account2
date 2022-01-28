package com.ensa.web.rest;

import com.ensa.domain.Benificiaire;
import com.ensa.repository.BenificiaireRepository;
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
 * REST controller for managing {@link com.ensa.domain.Benificiaire}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BenificiaireResource {

    private final Logger log = LoggerFactory.getLogger(BenificiaireResource.class);

    private static final String ENTITY_NAME = "accountApiBenificiaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BenificiaireRepository benificiaireRepository;

    public BenificiaireResource(BenificiaireRepository benificiaireRepository) {
        this.benificiaireRepository = benificiaireRepository;
    }

    /**
     * {@code POST  /benificiaires} : Create a new benificiaire.
     *
     * @param benificiaire the benificiaire to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new benificiaire, or with status {@code 400 (Bad Request)} if the benificiaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/benificiaires")
    public ResponseEntity<Benificiaire> createBenificiaire(@Valid @RequestBody Benificiaire benificiaire) throws URISyntaxException {
        log.debug("REST request to save Benificiaire : {}", benificiaire);
        if (benificiaire.getId() != null) {
            throw new BadRequestAlertException("A new benificiaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Benificiaire result = benificiaireRepository.save(benificiaire);
        return ResponseEntity
            .created(new URI("/api/benificiaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /benificiaires/:id} : Updates an existing benificiaire.
     *
     * @param id the id of the benificiaire to save.
     * @param benificiaire the benificiaire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated benificiaire,
     * or with status {@code 400 (Bad Request)} if the benificiaire is not valid,
     * or with status {@code 500 (Internal Server Error)} if the benificiaire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/benificiaires/{id}")
    public ResponseEntity<Benificiaire> updateBenificiaire(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Benificiaire benificiaire
    ) throws URISyntaxException {
        log.debug("REST request to update Benificiaire : {}, {}", id, benificiaire);
        if (benificiaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, benificiaire.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!benificiaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Benificiaire result = benificiaireRepository.save(benificiaire);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, benificiaire.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /benificiaires/:id} : Partial updates given fields of an existing benificiaire, field will ignore if it is null
     *
     * @param id the id of the benificiaire to save.
     * @param benificiaire the benificiaire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated benificiaire,
     * or with status {@code 400 (Bad Request)} if the benificiaire is not valid,
     * or with status {@code 404 (Not Found)} if the benificiaire is not found,
     * or with status {@code 500 (Internal Server Error)} if the benificiaire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/benificiaires/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Benificiaire> partialUpdateBenificiaire(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Benificiaire benificiaire
    ) throws URISyntaxException {
        log.debug("REST request to partial update Benificiaire partially : {}, {}", id, benificiaire);
        if (benificiaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, benificiaire.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!benificiaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Benificiaire> result = benificiaireRepository
            .findById(benificiaire.getId())
            .map(existingBenificiaire -> {
                return existingBenificiaire;
            })
            .map(benificiaireRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, benificiaire.getId().toString())
        );
    }

    /**
     * {@code GET  /benificiaires} : get all the benificiaires.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of benificiaires in body.
     */
    @GetMapping("/benificiaires")
    public List<Benificiaire> getAllBenificiaires() {
        log.debug("REST request to get all Benificiaires");
        return benificiaireRepository.findAll();
    }

    /**
     * {@code GET  /benificiaires/:id} : get the "id" benificiaire.
     *
     * @param id the id of the benificiaire to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the benificiaire, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/benificiaires/{id}")
    public ResponseEntity<Benificiaire> getBenificiaire(@PathVariable Long id) {
        log.debug("REST request to get Benificiaire : {}", id);
        Optional<Benificiaire> benificiaire = benificiaireRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(benificiaire);
    }

    /**
     * {@code DELETE  /benificiaires/:id} : delete the "id" benificiaire.
     *
     * @param id the id of the benificiaire to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/benificiaires/{id}")
    public ResponseEntity<Void> deleteBenificiaire(@PathVariable Long id) {
        log.debug("REST request to delete Benificiaire : {}", id);
        benificiaireRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
