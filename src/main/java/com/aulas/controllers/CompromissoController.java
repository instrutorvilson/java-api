package com.aulas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aulas.entidades.Compromisso;
import com.aulas.repository.CompromissoRepository;

@RestController
@RequestMapping("/")
public class CompromissoController {
    @Autowired
    CompromissoRepository repo;
     
    @GetMapping("/compromissos/contato")
    public ResponseEntity<List<Compromisso>> consultaCompromissoPeloNomeContato(@RequestParam("nome")
    String nome){
    	System.out.println("nome" + nome);
    	return ResponseEntity.ok(repo.consultaCompromissosPorNomeContato(nome));
    }
    
	@GetMapping("/compromissos")
    public ResponseEntity<List<Compromisso>> consultaCompromissos() {
    	return ResponseEntity.ok(repo.findAll());
    }
    
    @PostMapping("/compromissos")
    public ResponseEntity<Compromisso> salvarCompromisso(@RequestBody Compromisso compromisso){
    	return ResponseEntity.ok(repo.save(compromisso));
    }
}
