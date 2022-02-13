package com.ensa.service;


import com.ensa.domain.Benificiaire;
import com.ensa.domain.Client;
import com.ensa.domain.Compte;
import com.ensa.domain.Kyc;
import com.ensa.repository.BenificiaireRepository;
import com.ensa.repository.ClientRepository;
import liquibase.pro.packaged.B;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BenificiaireService {
    @Autowired
    private BenificiaireRepository benificiaireRepository;

    @Autowired
    private KycService kycService;

    @Autowired
    private ClientRepository clientRepository;


    public int saveBenificiaire(Kyc kyc,String numClient)
    {
        if (benificiaireRepository.findBenificiaireByKyc_Nom(kyc.getNumIdentite()).isPresent()) {
            return -1;
        } else {
            kycService.saveKyc(kyc);
            Benificiaire benificiaire = new Benificiaire();
            benificiaire.setKyc(kyc);
            Client client = clientRepository.findClientByKyc_NumIdentite(numClient);
            if (client != null) {
                benificiaire.setClient(client);
            }
             benificiaireRepository.save(benificiaire);
            return 1;
        }

    }

    public Benificiaire updateBenificiare(Benificiaire benificiaire)
    {
        Benificiaire benificiaire1 = benificiaireRepository.findById(benificiaire.getId()).orElseThrow();

        benificiaire1.setKyc(benificiaire.getKyc());
        benificiaire1.setClient(benificiaire.getClient());

        return benificiaireRepository.save(benificiaire1);
    }

    public Benificiaire updateBenificiairePartial(Benificiaire benificiaire)
    {
        Benificiaire benificiaire1 = benificiaireRepository.findById(benificiaire.getId()).orElseThrow();

        if(benificiaire.getClient() != null){ benificiaire1.setClient(benificiaire.getClient());}
        if(benificiaire.getKyc() != null){ benificiaire1.setKyc(benificiaire.getKyc());}

        return benificiaireRepository.save(benificiaire1);
    }

    public Benificiaire getByKyc(Kyc kyc) {
        return benificiaireRepository.findByKyc(kyc);
    }

    public void ajoutBenifToClient(String numIdentiBenef, String numIdent)
    {
        Kyc kycBene = kycService.getByNumIdentite(numIdentiBenef);
        Kyc kyc = kycService.getByNumIdentite(numIdent);

        Client client = clientRepository.findByKyc(kyc);
        Benificiaire benificiaire = benificiaireRepository.findByKyc(kycBene);

        Set<Benificiaire> benificiaires = client.getBenificiaires();
        benificiaires.add(benificiaire);
        client.setBenificiaires(benificiaires);

        clientRepository.save(client);
    }

    public Benificiaire getBenefByKyc(String numIdent)
    {
        Kyc kycBene = kycService.getByNumIdentite(numIdent);
        return benificiaireRepository.findByKyc(kycBene);
    }

    public void deleteByIdentity(String numIdent)
    {
        Kyc kycBene = kycService.getByNumIdentite(numIdent);
        Benificiaire benificiaire = benificiaireRepository.findByKyc(kycBene);

        kycService.deleteKyc(kycBene.getId());
        benificiaireRepository.delete(benificiaire);
    }

    public int deleteById(Long id) {
        Optional<Benificiaire> benificiaire = benificiaireRepository.findBenificiaireById(id);
        if (benificiaire.isPresent()) {
            benificiaireRepository.delete(benificiaire.get());
            return 1;
        } else {
            return -1;
        }
    }

    public List<Benificiaire> getListOfBenificiairsBynumClient(String numIdentityClient) {
        return benificiaireRepository.findBenificiairesByClient_Kyc_NumIdentite(numIdentityClient);
    }

    public Benificiaire getBenificiairBynomAndNumClient(String nomBenificiair, String numClient) {
        Optional<Benificiaire> benificiaire=benificiaireRepository.findBenificiaireByKyc_NomAndClient_Kyc_NumIdentite(nomBenificiair, numClient);
        return benificiaire.orElse(null);
    }

}
