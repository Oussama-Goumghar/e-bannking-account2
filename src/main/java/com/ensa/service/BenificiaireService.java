package com.ensa.service;


import com.ensa.domain.Benificiaire;
import com.ensa.domain.Client;
import com.ensa.domain.Compte;
import com.ensa.domain.Kyc;
import com.ensa.repository.BenificiaireRepository;
import com.ensa.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BenificiaireService {

    @Autowired
    private BenificiaireRepository benificiaireRepository;

    @Autowired
    private KycService kycService;

    @Autowired
    private ClientRepository clientRepository;


    public Benificiaire saveBenificiaire(Kyc kyc)
    {
        kycService.saveKyc(kyc);
        Benificiaire benificiaire = new Benificiaire();
        benificiaire.setKyc(kyc);

        return benificiaireRepository.save(benificiaire);
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
}
