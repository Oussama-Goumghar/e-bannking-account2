package com.ensa.web.rest;

import com.ensa.domain.Benificiaire;
import com.ensa.domain.Kyc;
import com.ensa.repository.BenificiaireRepository;
import com.ensa.service.BenificiaireService;
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

    @Autowired
    private BenificiaireService benificiaireService;


    @PostMapping("/benificiaires")
    public ResponseEntity<Benificiaire> createBenificiaire(@Valid @RequestBody Kyc kyc) throws URISyntaxException {
        log.debug("REST request to save Benificiaire : {}", kyc);
        if (kyc.getId() != null) {
            throw new BadRequestAlertException("A new benificiaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Benificiaire result = benificiaireService.saveBenificiaire(kyc);
        return ResponseEntity
            .created(new URI("/api/benificiaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/benificiaires")
    public ResponseEntity<Benificiaire> updateBenificiaire(
        @Valid @RequestBody Benificiaire benificiaire
    ){

        Benificiaire result = benificiaireService.updateBenificiare(benificiaire);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, benificiaire.getId().toString()))
            .body(result);
    }


    @PatchMapping(value = "/benificiaires", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Benificiaire> partialUpdateBenificiaire(
        @NotNull @RequestBody Benificiaire benificiaire
    ) throws URISyntaxException {

        Optional<Benificiaire> result = Optional.ofNullable(benificiaireService.updateBenificiairePartial(benificiaire));

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, benificiaire.getId().toString())
        );
    }


    @GetMapping("/benificiaires")
    public Benificiaire getByKyc(@RequestBody Kyc kyc) {
        log.debug("REST request to get all Benificiaires");
        return benificiaireService.getByKyc(kyc);
    }


    @GetMapping("/benificiaires/{numIdent}")
    public ResponseEntity<Benificiaire> getBenificiaireByIdentity(@PathVariable String numIdent) {
        log.debug("REST request to get Benificiaire : {}", numIdent);
        Optional<Benificiaire> benificiaire = Optional.ofNullable(benificiaireService.getBenefByKyc(numIdent));
        return ResponseUtil.wrapOrNotFound(benificiaire);
    }


    @DeleteMapping("/benificiaires/{numIdent}")
    public ResponseEntity<Void> deleteBenificiaire(@PathVariable String numIdent) {
        log.debug("REST request to delete Benificiaire : {}", numIdent);
        benificiaireService.deleteByIdentity(numIdent);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, numIdent))
            .build();
    }
}
