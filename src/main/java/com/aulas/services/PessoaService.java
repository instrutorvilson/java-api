package com.aulas.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aulas.entidades.Pessoa;
import com.aulas.repository.PessoaRepository;

@Service
public class PessoaService {
   @Autowired
   PessoaRepository repo;
   
   public Pessoa consultar(Long idpessoa) {
	   Optional<Pessoa> obj = repo.findById(idpessoa);
	   return obj.orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));
   }
   
   public Pessoa salvar(Pessoa pessoa) {
	   return repo.save(pessoa);
   }
   
   public Pessoa alterar(long idpessoa, Pessoa pessoa) {
	   Optional<Pessoa> obj = repo.findById(idpessoa);
	   Pessoa p = obj.orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));
	   p.setNome(pessoa.getNome());
	   return repo.save(p);
   }
   
   public List<Pessoa> consultarTodos(){
	   return repo.findAll();
   }
}
