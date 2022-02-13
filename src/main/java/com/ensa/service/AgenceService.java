package com.ensa.service;


import com.ensa.domain.Agence;
import com.ensa.repository.AgenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgenceService {

    @Autowired
    private AgenceRepository agenceRepository;

    public Agence saveAgence(Agence agence)
    {
        if(agenceRepository.existsByReference(agence.getReference()))
        {
            Agence agence1 = agenceRepository.findByReference(agence.getReference());
            return agence1;
        }
        else
        {
            return agenceRepository.save(agence);
        }
    }

    public void deleteAgence(String refAgen)
    {
        Agence agence = agenceRepository.findByReference(refAgen);
        agenceRepository.delete(agence);
    }

    public Agence updateAgence(Agence agence,String ref)
    {
        if(ref.equals(agence.getReference()))
        {
            Agence agence1 = agenceRepository.findByReference(ref);

            agence1.setReference(agence.getReference());
            agence1.setVille(agence.getVille());
            agence1.setAddress(agence.getVille());
            agence1.setPlafondMontant(agence.getPlafondMontant());
            agence1.setPlafondTransaction(agence.getPlafondTransaction());

            return agenceRepository.save(agence1);
        }
        else return null;
    }

    public Agence updateAgencePartial(Agence agence,String ref)
    {
        if(ref.equals(agence.getReference())) {
            Agence agence1 = agenceRepository.findById(agence.getId()).orElseThrow(IllegalStateException::new);

            if (agence.getAddress() != null) {
                agence1.setAddress(agence.getAddress());
            }
            if (agence.getVille() != null) {
                agence1.setVille(agence.getVille());
            }
            if (agence.getReference() != null) {
                agence1.setReference(agence.getReference());
            }
            if (agence.getPlafondMontant() != null) {
                agence1.setPlafondMontant(agence.getPlafondMontant());
            }
            if (agence.getPlafondTransaction() != null) {
                agence1.setPlafondTransaction(agence.getPlafondTransaction());
            }

            return agenceRepository.save(agence1);
        }
        else return null;

    }

    public List<Agence> getAllAgences()
    {
        return agenceRepository.findAll();
    }

    public Optional<Agence> getAgenceById(Long id)
    {
        return agenceRepository.findById(id);
    }

    public Agence getAgenceByRef(String ref)
    {
        return agenceRepository.findByReference(ref);
    }

    public boolean existsById(Long id) {
        return agenceRepository.existsById(id);
    }

    public void setPlafondAmount(String refAgen,Double montant)
    {
        Agence agence = agenceRepository.findByReference(refAgen);
        agence.setPlafondMontant(montant);
        agenceRepository.save(agence);
    }

    public void setPlafondTransa(String refAgen,Integer montant)
    {
        Agence agence = agenceRepository.findByReference(refAgen);
        agence.setPlafondTransaction(montant);
        agenceRepository.save(agence);
    }
}
