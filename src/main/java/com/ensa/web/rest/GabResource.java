package com.ensa.web.rest;

import com.ensa.domain.Gab;
import com.ensa.repository.GabRepository;
import com.ensa.service.GabService;
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

    @Autowired
    private GabService gabService;



    @PostMapping("/gabs")
    public ResponseEntity<Gab> createGab(@Valid @RequestBody Gab gab) throws URISyntaxException {

        Gab result = gabService.saveGab(gab);
        return ResponseEntity
            .created(new URI("/api/gabs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/gabs")
    public ResponseEntity<Gab> updateGab( @Valid @RequestBody Gab gab)
        throws URISyntaxException {

        Gab result = gabService.updateGab(gab);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gab.getId().toString()))
            .body(result);
    }


    @PatchMapping(value = "/gabs", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Gab> partialUpdateGab( @NotNull @RequestBody Gab gab)
        throws URISyntaxException {


        Gab result = gabService.updateGabPartial(gab);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gab.getId().toString()))
            .body(result);
    }


    @GetMapping("/gabs")
    public List<Gab> getAllGabs() {
        log.debug("REST request to get all Gabs");
        return gabService.getAllGabs();
    }



    @GetMapping("/gabs/{id}")
    public ResponseEntity<Gab> getGab(@PathVariable Long id) {
        log.debug("REST request to get Gab : {}", id);
        Optional<Gab> gab = Optional.ofNullable(gabService.getGabById(id));
        return ResponseUtil.wrapOrNotFound(gab);
    }


    @DeleteMapping("/gabs/{id}")
    public ResponseEntity<Void> deleteGab(@PathVariable Long id) {
        log.debug("REST request to delete Gab : {}", id);
        gabService.deleteGab(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
