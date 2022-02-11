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
        return agenceRepository.save(agence);
    }

    public void deleteAgence(String refAgen)
    {
        Agence agence = agenceRepository.findByReference(refAgen);
        agenceRepository.delete(agence);
    }

    public Agence updateAgence(Agence agence)
    {
        Agence agence1 = agenceRepository.findById(agence.getId()).orElseThrow(IllegalStateException::new);

        agence1.setReference(agence.getReference());
        agence1.setVille(agence.getVille());
        agence1.setAddress(agence.getVille());
        agence1.setPlafondMontant(agence.getPlafondMontant());
        agence1.setPlafondTransaction(agence.getPlafondTransaction());

        return agenceRepository.save(agence1);

    }

    public Agence updateAgencePartial(Agence agence)
    {
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
}
