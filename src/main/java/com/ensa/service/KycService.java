package com.ensa.service;


import com.ensa.domain.Kyc;
import com.ensa.repository.KycRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KycService {

    @Autowired
    private KycRepository kycRepository;

    public Kyc saveKyc(Kyc kyc)
    {
        return kycRepository.save(kyc);
    }

    public Kyc updateKyc(Kyc kyc)
    {
        Kyc kyc1 = kycRepository.findById(kyc.getId()).orElseThrow();

        kyc1.setTitre(kyc.getTitre());
        kyc1.setNom(kyc.getNom());
        kyc1.setPrenom(kyc.getPrenom());
        kyc1.setTypeIdentite(kyc.getTypeIdentite());
        kyc1.setNumIdentite(kyc.getNumIdentite());
        kyc1.setValidateTimeIdentite(kyc.getValidateTimeIdentite());
        kyc1.setProfession(kyc.getProfession());
        kyc1.setNationalite(kyc.getNationalite());
        kyc1.setAddress(kyc.getAddress());
        kyc1.setGsm(kyc.getGsm());
        kyc1.setEmail(kyc.getEmail());

        return kycRepository.save(kyc1);
    }

    public Kyc updateKycPartial(Kyc kyc)
    {
        Kyc kyc1 = kycRepository.findById(kyc.getId()).orElseThrow();

        if(kyc.getTitre() != null){kyc1.setTitre(kyc.getTitre());}
        if(kyc.getNom() != null){kyc1.setNom(kyc.getNom());}
        if(kyc.getPrenom() != null){kyc1.setPrenom(kyc.getPrenom());}
        if(kyc.getTypeIdentite() != null){kyc1.setTypeIdentite(kyc.getTypeIdentite());}
        if(kyc.getNumIdentite() != null){kyc1.setTypeIdentite(kyc.getTypeIdentite());}
        if(kyc.getValidateTimeIdentite() != null){kyc1.setValidateTimeIdentite(kyc.getValidateTimeIdentite());}
        if(kyc.getProfession() != null){kyc1.setProfession(kyc.getProfession());}
        if(kyc.getNationalite() != null){kyc1.setNationalite(kyc.getNationalite());}
        if(kyc.getAddress() != null){kyc1.setAddress(kyc.getAddress());}
        if(kyc.getGsm() != null){kyc1.setGsm(kyc.getGsm());}
        if(kyc.getEmail() != null){kyc1.setEmail(kyc.getEmail());}


        return kycRepository.save(kyc1);
    }

    public List<Kyc> getAllKyc(){ return kycRepository.findAll();}

    public Kyc getKycById(Long id){return kycRepository.findById(id).orElseThrow();}

    public void deleteKyc(Long id)
    {
        Kyc kyc = kycRepository.findById(id).orElseThrow();
        kycRepository.delete(kyc);
    }

    public boolean existsById(Long id) {
        return kycRepository.existsById(id);
    }
}
