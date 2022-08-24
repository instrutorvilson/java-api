package com.aulas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aulas.entidades.Compromisso;
import com.aulas.repository.CompromissoRepository;

@Service
public class CompromissoService {
    @Autowired
    CompromissoRepository repo;
    
    public Compromisso salvar(Compromisso compromisso) {
    	return repo.save(compromisso);
    }
}
