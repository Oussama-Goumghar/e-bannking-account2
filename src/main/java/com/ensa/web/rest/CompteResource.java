package com.ensa.web.rest;

import com.ensa.domain.Compte;
import com.ensa.repository.CompteRepository;
import com.ensa.service.CompteService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ensa.domain.Compte}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CompteResource {

    private final Logger log = LoggerFactory.getLogger(CompteResource.class);

    private static final String ENTITY_NAME = "accountApiCompte";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private CompteService compteService;


    /**
     * {@code POST  /comptes} : Create a new compte.
     *
     * @param compte the compte to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new compte, or with status {@code 400 (Bad Request)} if the compte has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comptes")
    public ResponseEntity<Compte> createCompte(@Valid @RequestBody Compte compte) throws URISyntaxException {
        log.debug("REST request to save Compte : {}", compte);
        Compte result = compteService.saveCompte(compte);
        return ResponseEntity
            .created(new URI("/api/comptes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comptes/:id} : Updates an existing compte.
     *
     * @param id the id of the compte to save.
     * @param compte the compte to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compte,
     * or with status {@code 400 (Bad Request)} if the compte is not valid,
     * or with status {@code 500 (Internal Server Error)} if the compte couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comptes/{id}")
    public ResponseEntity<Compte> updateCompte(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Compte compte
    ) throws URISyntaxException {
        log.debug("REST request to update Compte : {}, {}", id, compte);
        if (compte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, compte.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!compteService.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Compte result = compteService.updateCompte(compte);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compte.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /comptes/:id} : Partial updates given fields of an existing compte, field will ignore if it is null
     *
     * @param id the id of the compte to save.
     * @param compte the compte to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compte,
     * or with status {@code 400 (Bad Request)} if the compte is not valid,
     * or with status {@code 404 (Not Found)} if the compte is not found,
     * or with status {@code 500 (Internal Server Error)} if the compte couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/comptes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Compte> partialUpdateCompte(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Compte compte
    ) throws URISyntaxException {
        log.debug("REST request to partial update Compte partially : {}, {}", id, compte);
        if (compte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, compte.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!compteService.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Compte compte1 = compteService.updateComptePartial(compte);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compte.getId().toString()))
            .body(compte1);
    }

    /**
     * {@code GET  /comptes} : get all the comptes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comptes in body.
     */
    @GetMapping("/comptes")
    public List<Compte> getAllComptes() {
        log.debug("REST request to get all Comptes");
        return compteService.getAllComptes();
    }

    /**
     * {@code GET  /comptes/:id} : get the "id" compte.
     *
     * @param id the id of the compte to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the compte, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comptes/{id}")
    public ResponseEntity<Compte> getCompteById(@PathVariable Long id) {
        log.debug("REST request to get Compte : {}", id);
        Optional<Compte> compte = compteService.getCompteById(id);
        return ResponseUtil.wrapOrNotFound(compte);
    }


    /**
     * {@code GET  /comptes/rib/:rib} : get the "rib" compte.
     *
     * @param rib the rib of the compte to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the compte, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comptes/rib/{rib}")
    public ResponseEntity<Compte> getCompteByRib(@PathVariable String rib) {
        log.debug("REST request to get Compte : {}", rib);
        Optional<Compte> compte = Optional.ofNullable(compteService.getCompteByRib(rib));
        return (ResponseEntity<Compte>) ResponseUtil.wrapOrNotFound(compte);
    }


    /**
     * {@code DELETE  /comptes/:id} : delete the "id" compte.
     *
     * @param id the id of the compte to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comptes/{id}")
    public ResponseEntity<Void> deleteCompteById(@PathVariable Long id) {
        log.debug("REST request to delete Compte : {}", id);
        compteService.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code DELETE  /comptes/rib/:rib} : delete the "rib" compte.
     *
     * @param rib the id of the compte to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comptes/rib/{rib}")
    public ResponseEntity<Void> deleteCompteByRib(@PathVariable String rib) {
        log.debug("REST request to delete Compte : {}", rib);
        compteService.deleteByRib(rib);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, rib.toString()))
            .build();
    }
}
