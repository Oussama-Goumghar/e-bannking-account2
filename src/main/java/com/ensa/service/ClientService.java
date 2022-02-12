package com.ensa.service;


import com.ensa.domain.*;
import com.ensa.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private KycService kycService;

    @Autowired
    private AgenceService agenceService;

    @Autowired
    private CompteService compteService;

    @Autowired
    private BenificiaireService benificiaireService;

    public Client saveClient(Kyc kyc,String refAge)
    {
        Agence agence = agenceService.getAgenceByRef(refAge);

        Client client = new Client();
        kycService.saveKyc(kyc);
        client.setKyc(kyc);
        client.setAgence(agence);

        return clientRepository.save(client);
    }

    public void ajouterCompte(Compte compte,String numIdent)
    {
        compteService.saveCompte(compte);

        Kyc kyc = kycService.getByNumIdentite(numIdent);
        Client client = clientRepository.findByKyc(kyc);
        client.setCompteClient(compte);

        clientRepository.save(client);
    }

    public void suppCompte(String numIdent,String rib)
    {
        Kyc kyc = kycService.getByNumIdentite(numIdent);
        Client client = clientRepository.findByKyc(kyc);
        Compte compte = compteService.getCompteByRib(rib);
        client.setCompteClient(null);
        compteService.deleteByRib(rib);
        clientRepository.save(client);
    }



    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    public List<Client> getClientsByAgence(String refAgence){
        Agence agence = agenceService.getAgenceByRef(refAgence);
        return clientRepository.findByAgence(agence);
    }

    public Client getByKyc(Kyc kyc) {
        return clientRepository.findByKyc(kyc);
    }

    public Client getByNumIdentite(String numIdent){
        Kyc kyc = kycService.getByNumIdentite(numIdent);
        return clientRepository.findByKyc(kyc);
    }
}
