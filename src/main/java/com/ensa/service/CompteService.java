package com.ensa.service;


import com.ensa.domain.Client;
import com.ensa.domain.Compte;
import com.ensa.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CompteService {

    @Autowired
    public CompteRepository compteRepository;

    public Compte saveCompte(Compte compte)
    {
        return compteRepository.save(compte);
    }

    public Compte updateCompte(Compte compte)
    {
        Compte compte1 = compteRepository.findById(compte.getId()).orElseThrow();
        compte1.setDateCreation(compte.getDateCreation());
        compte1.setRib(compte.getRib());
        compte1.setSolde(compte.getSolde());
        compte1.setStatus(compte.getStatus());

        return compteRepository.save(compte1);
    }

    public Compte updateComptePartial(Compte compte)
    {
        Compte compte1 = compteRepository.findById(compte.getId()).orElseThrow();
      /*  if(compte.getClient()!=null)
        {
            compte1.setClient(compte.getClient());
        }*/
        if(compte.getDateCreation()!=null)
        {
            compte1.setDateCreation(compte.getDateCreation());
        }
        if(compte.getRib()!=null)
        {
            compte1.setRib(compte.getRib());
        }
        if(compte.getSolde()!=null)
        {
            compte1.setSolde(compte.getSolde());
        }
        if(compte.getStatus()!=null)
        {
            compte1.setStatus(compte.getStatus());
        }

        return compteRepository.save(compte1);
    }

    public List<Compte> getAllComptes()
    {
        return compteRepository.findAll();
    }

    public Optional<Compte> getCompteById(Long id)
    {
        return compteRepository.findById(id);
    }

    public Compte getCompteByRib(String rib)
    {
        return compteRepository.findByRib(rib);
    }

    public boolean existsById(Long id)
    {
        return compteRepository.existsById(id);
    }

    public void deleteById(Long id)
    {
        Compte compte = compteRepository.findById(id).orElseThrow();
        compteRepository.delete(compte);
    }

    public void deleteByRib(String rib)
    {
        Compte compte = compteRepository.findByRib(rib);
        compteRepository.delete(compte);
    }


    public Compte createAccountAgent() {
        long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;

        Compte compte = new Compte();
        compte.setSolde((double) 10000);
        compte.setDateCreation(LocalDate.now());
        compte.setRib(number+"");
        compte.setStatus(RibStatus.ACTIVER.name());

        return compteRepository.save(compte);
    }

    public Compte createAccountClient() {
        long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;

        Compte compte = new Compte();
        compte.setSolde((double) 0);
        compte.setDateCreation(LocalDate.now());
        compte.setRib(number+"");
        compte.setStatus(RibStatus.ACTIVER.name());

        return compteRepository.save(compte);
    }
}
