package com.ensa.web.rest;

import com.ensa.domain.Agence;
import com.ensa.repository.AgenceRepository;
import com.ensa.service.AgenceService;
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
 * REST controller for managing {@link com.ensa.domain.Agence}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AgenceResource {

    private final Logger log = LoggerFactory.getLogger(AgenceResource.class);

    private static final String ENTITY_NAME = "accountApiAgence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private AgenceService agenceService;



    @PostMapping("/agences")
    public ResponseEntity<Agence> createAgence(@Valid @RequestBody Agence agence) throws URISyntaxException {
        log.debug("REST request to save Agence : {}", agence);

        Agence result = agenceService.saveAgence(agence);
        return ResponseEntity
            .created(new URI("/api/agences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/agences/plafamount/{refAgence}/{montant}")
    public void setPlafondAmount(@PathVariable String refAgence,@PathVariable Double montant) throws URISyntaxException {

        agenceService.setPlafondAmount(refAgence,montant);
    }

    @PostMapping("/agences/plaftrans/{refAgence}/{montant}")
    public void setPlafondTrans(@PathVariable String refAgence,@PathVariable Integer montant) throws URISyntaxException {

        agenceService.setPlafondTransa(refAgence,montant);
    }


    @PutMapping("/agences/{ref}")
    public ResponseEntity<Agence> updateAgence(@PathVariable String ref,
                                               @Valid @RequestBody Agence agence
    ) throws URISyntaxException {

        Agence result = agenceService.updateAgence(agence,ref);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agence.getId().toString()))
            .body(result);
    }


    @PatchMapping(value = "/agences/{ref}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Agence> partialUpdateAgence(@PathVariable String ref,
                                                      @NotNull @RequestBody Agence agence
    ) throws URISyntaxException {
        log.debug("REST request to partial update Agence partially : {}, {}", agence);


        Agence result = agenceService.updateAgencePartial(agence,ref);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agence.getId().toString()))
            .body(result);
    }


    @GetMapping("/agences")
    public List<Agence> getAllAgences() {
        log.debug("REST request to get all Agences");
        return agenceService.getAllAgences();
    }


    @GetMapping("/agences/{id}")
    public ResponseEntity<Agence> getAgence(@PathVariable Long id) {
        log.debug("REST request to get Agence : {}", id);
        Optional<Agence> agence = agenceService.getAgenceById(id);
        return ResponseUtil.wrapOrNotFound(agence);
    }


    @GetMapping("/agences/reference/{ref}")
    public ResponseEntity<Agence> getAgenceByRef(@PathVariable String ref) {
        log.debug("REST request to get Agence : {}", ref);
        Optional<Agence> agence = Optional.ofNullable(agenceService.getAgenceByRef(ref));
        return ResponseUtil.wrapOrNotFound(agence);
    }


    @DeleteMapping("/agences/{refAgence}")
    public ResponseEntity<Void> deleteAgence(@PathVariable String refAgence) {
        log.debug("REST request to delete Agence : {}", refAgence);
        agenceService.deleteAgence(refAgence);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, refAgence.toString()))
            .build();
    }
}
