package com.ensa.web.rest;

import com.ensa.domain.Kyc;
import com.ensa.repository.KycRepository;
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
 * REST controller for managing {@link com.ensa.domain.Kyc}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class KycResource {

    private final Logger log = LoggerFactory.getLogger(KycResource.class);

    private static final String ENTITY_NAME = "accountApiKyc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KycRepository kycRepository;

    public KycResource(KycRepository kycRepository) {
        this.kycRepository = kycRepository;
    }

    /**
     * {@code POST  /kycs} : Create a new kyc.
     *
     * @param kyc the kyc to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kyc, or with status {@code 400 (Bad Request)} if the kyc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kycs")
    public ResponseEntity<Kyc> createKyc(@Valid @RequestBody Kyc kyc) throws URISyntaxException {
        log.debug("REST request to save Kyc : {}", kyc);
        if (kyc.getId() != null) {
            throw new BadRequestAlertException("A new kyc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Kyc result = kycRepository.save(kyc);
        return ResponseEntity
            .created(new URI("/api/kycs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kycs/:id} : Updates an existing kyc.
     *
     * @param id the id of the kyc to save.
     * @param kyc the kyc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kyc,
     * or with status {@code 400 (Bad Request)} if the kyc is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kyc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kycs/{id}")
    public ResponseEntity<Kyc> updateKyc(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Kyc kyc)
        throws URISyntaxException {
        log.debug("REST request to update Kyc : {}, {}", id, kyc);
        if (kyc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kyc.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kycRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Kyc result = kycRepository.save(kyc);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kyc.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /kycs/:id} : Partial updates given fields of an existing kyc, field will ignore if it is null
     *
     * @param id the id of the kyc to save.
     * @param kyc the kyc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kyc,
     * or with status {@code 400 (Bad Request)} if the kyc is not valid,
     * or with status {@code 404 (Not Found)} if the kyc is not found,
     * or with status {@code 500 (Internal Server Error)} if the kyc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/kycs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Kyc> partialUpdateKyc(@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody Kyc kyc)
        throws URISyntaxException {
        log.debug("REST request to partial update Kyc partially : {}, {}", id, kyc);
        if (kyc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kyc.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kycRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Kyc> result = kycRepository
            .findById(kyc.getId())
            .map(existingKyc -> {
                if (kyc.getTitre() != null) {
                    existingKyc.setTitre(kyc.getTitre());
                }
                if (kyc.getNom() != null) {
                    existingKyc.setNom(kyc.getNom());
                }
                if (kyc.getPrenom() != null) {
                    existingKyc.setPrenom(kyc.getPrenom());
                }
                if (kyc.getTypeIdentite() != null) {
                    existingKyc.setTypeIdentite(kyc.getTypeIdentite());
                }
                if (kyc.getNumIdentite() != null) {
                    existingKyc.setNumIdentite(kyc.getNumIdentite());
                }
                if (kyc.getValidateTimeIdentite() != null) {
                    existingKyc.setValidateTimeIdentite(kyc.getValidateTimeIdentite());
                }
                if (kyc.getProfession() != null) {
                    existingKyc.setProfession(kyc.getProfession());
                }
                if (kyc.getNationalite() != null) {
                    existingKyc.setNationalite(kyc.getNationalite());
                }
                if (kyc.getAddress() != null) {
                    existingKyc.setAddress(kyc.getAddress());
                }
                if (kyc.getGsm() != null) {
                    existingKyc.setGsm(kyc.getGsm());
                }
                if (kyc.getEmail() != null) {
                    existingKyc.setEmail(kyc.getEmail());
                }

                return existingKyc;
            })
            .map(kycRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kyc.getId().toString())
        );
    }

    /**
     * {@code GET  /kycs} : get all the kycs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kycs in body.
     */
    @GetMapping("/kycs")
    public List<Kyc> getAllKycs() {
        log.debug("REST request to get all Kycs");
        return kycRepository.findAll();
    }

    /**
     * {@code GET  /kycs/:id} : get the "id" kyc.
     *
     * @param id the id of the kyc to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kyc, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kycs/{id}")
    public ResponseEntity<Kyc> getKyc(@PathVariable Long id) {
        log.debug("REST request to get Kyc : {}", id);
        Optional<Kyc> kyc = kycRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kyc);
    }

    /**
     * {@code DELETE  /kycs/:id} : delete the "id" kyc.
     *
     * @param id the id of the kyc to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kycs/{id}")
    public ResponseEntity<Void> deleteKyc(@PathVariable Long id) {
        log.debug("REST request to delete Kyc : {}", id);
        kycRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
