package com.ensa.web.rest;

import com.ensa.domain.Benificiaire;
import com.ensa.domain.Client;
import com.ensa.domain.Compte;
import com.ensa.domain.Kyc;
import com.ensa.repository.ClientRepository;
import com.ensa.service.BenificiaireService;
import com.ensa.service.ClientService;
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
 * REST controller for managing {@link com.ensa.domain.Client}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ClientResource {

    private final Logger log = LoggerFactory.getLogger(ClientResource.class);

    private static final String ENTITY_NAME = "accountApiClient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private ClientService clientService;

    @Autowired
    private BenificiaireService benificiaireService;

    /**
     * {@code POST  /clients} : Create a new client.
     *
     * @param kyc the client to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new client, or with status {@code 400 (Bad Request)} if the client has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clients/{refAgence}")
    public ResponseEntity<Client> createClient(
        @PathVariable String refAgence,
        @Valid @RequestBody Kyc kyc) throws URISyntaxException {
        log.debug("REST request to save Client : {}", kyc);

        Client result = clientService.saveClient(kyc,refAgence);
        return ResponseEntity
            .created(new URI("/api/clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/clients/comptes/{numIdent}")
    public ResponseEntity<Compte> addAccountToClient(@PathVariable String numIdent,
                                                   @RequestBody Compte compte){
        clientService.ajouterCompte(compte,numIdent);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/clients/beneficiares/{numIdentBenif}/{numIdent}")
    public ResponseEntity<Client> addAccountToClient(@PathVariable String numIdentBenif,
                                                     @PathVariable String numIdent){
        benificiaireService.ajoutBenifToClient(numIdentBenif,numIdent);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/clients")
    public List<Client> getAllClients() {
        log.debug("REST request to get all Clients");
        return clientService.getAllClients();
    }


    @GetMapping("/clients/agence/{refAgen}")
    public List<Client> getAllClientsByAgence(@PathVariable String refAgen) {
        log.debug("REST request to get all Clients");
        return clientService.getClientsByAgence(refAgen);
    }


    @GetMapping("/clients/identite/{numIdent}")
    public Client getClientByIdentity(@PathVariable String numIdent) {
        log.debug("REST request to get all Clients");
        return clientService.getByNumIdentite(numIdent);
    }

    @DeleteMapping("/clients/{numIdent}/{rib}")
    public ResponseEntity<Void> deleteClient(@PathVariable String numIdent,@PathVariable String rib ) {
        log.debug("REST request to delete Client : {}", numIdent,rib);
        clientService.suppCompte(numIdent,rib);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, rib.toString()))
            .build();
    }
}
