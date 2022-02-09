package com.ensa.service;


import com.ensa.domain.Agence;
import com.ensa.domain.Gab;
import com.ensa.repository.GabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GabService {

    @Autowired
    private GabRepository gabRepository;

    public Gab saveGab(Gab gab)
    {
        return gabRepository.save(gab);
    }

    public Gab updateGab(Gab gab)
    {
        Gab gab1 = gabRepository.findById(gab.getId()).orElseThrow(IllegalStateException::new);

        gab1.setAddress(gab.getAddress());
        gab1.setFond(gab.getFond());
        gab1.setAgence(gab.getAgence());

        return gabRepository.save(gab1);
    }

    public Gab updateGabPartial(Gab gab)
    {
        Gab gab1 = gabRepository.findById(gab.getId()).orElseThrow(IllegalStateException::new);

        if(gab.getAddress()!= null)
        {
            gab1.setAddress(gab.getAddress());
        }
        if(gab.getFond() != null)
        {
            gab1.setFond(gab.getFond());
        }
        if(gab.getAgence()!= null)
        {
            gab1.setAgence(gab.getAgence());
        }

        return gabRepository.save(gab1);
    }

    public Gab getGabById(Long id){
        return gabRepository.findById(id).orElseThrow(IllegalStateException::new);
    }

    public List<Gab> getAllGabs(){
        return gabRepository.findAll();
    }

    public Gab getGabByAgence(Agence agence){
        Gab gab = gabRepository.findByAgence(agence);
        return gab;
    }

    public boolean existsById(Long id) {
        return gabRepository.existsById(id);
    }

    public void deleteGab(Long id)
    {
        Gab gab = gabRepository.findById(id).orElseThrow(IllegalStateException::new);
        gabRepository.delete(gab);
    }
}
