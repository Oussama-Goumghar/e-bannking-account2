package com.ensa.web.rest;

import com.ensa.domain.Kyc;
import com.ensa.repository.KycRepository;
import com.ensa.service.KycService;
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

    @Autowired
    private KycService kycService;



    @PostMapping("/kycs")
    public ResponseEntity<Kyc> createKyc(@Valid @RequestBody Kyc kyc) throws URISyntaxException {
        log.debug("REST request to save Kyc : {}", kyc);
        if (kyc.getId() != null) {
            throw new BadRequestAlertException("A new kyc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Kyc result = kycService.saveKyc(kyc);
        return ResponseEntity
            .created(new URI("/api/kycs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/kycs")
    public ResponseEntity<Kyc> updateKyc( @Valid @RequestBody Kyc kyc)
        throws URISyntaxException {
        Kyc result = kycService.updateKyc(kyc);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kyc.getId().toString()))
            .body(result);
    }


    @PatchMapping(value = "/kycs", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Kyc> partialUpdateKyc( @NotNull @RequestBody Kyc kyc)
        throws URISyntaxException {
        Kyc result = kycService.updateKycPartial(kyc);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kyc.getId().toString()))
            .body(result);

    }


    @GetMapping("/kycs")
    public List<Kyc> getAllKycs() {
        log.debug("REST request to get all Kycs");
        return kycService.getAllKyc();
    }


    @GetMapping("/kycs/{id}")
    public ResponseEntity<Kyc> getKyc(@PathVariable Long id) {
        log.debug("REST request to get Kyc : {}", id);
        Optional<Kyc> kyc = Optional.ofNullable(kycService.getKycById(id));
        return ResponseUtil.wrapOrNotFound(kyc);
    }


    @DeleteMapping("/kycs/{id}")
    public ResponseEntity<Void> deleteKyc(@PathVariable Long id) {
        log.debug("REST request to delete Kyc : {}", id);
        kycService.deleteKyc(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
