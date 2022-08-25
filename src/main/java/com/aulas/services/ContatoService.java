package com.aulas.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aulas.entidades.Contato;
import com.aulas.repository.ContatoRepository;
import com.aulas.services.dto.ContatoDTO;

@Service
public class ContatoService {
	@Autowired
    ContatoRepository repo;

	@Transactional
	public ContatoDTO salvar(Contato contato) {	
		/*if(contato.getFone().length() != 14) {
			throw new ValidacaoException("Telefone inválido");
		}
		
		if(!contato.getEmail().contains("@")) {
			throw new ValidacaoException("Email inválido");
		}*/
		
		Contato ct = repo.save(contato);		
		ContatoDTO contatoDTO = new ContatoDTO(ct);
		return contatoDTO;
		
	}	
	
	@Transactional( readOnly = true)
	public List<ContatoDTO> consultarContatos(){
	    List<Contato> contatos = repo.findAll();
	    List<ContatoDTO> contatosDTO = new ArrayList();
	    for(Contato ct : contatos) {
	    	contatosDTO.add(new ContatoDTO(ct));
	    }
		return contatosDTO;
	}
	
	@Transactional( readOnly = true)
	public  ContatoDTO consultarContatoPorId(Long idcontato) {
	   Optional<Contato> opcontato = repo.findById(idcontato);
	   Contato ct = opcontato.orElseThrow(() -> new EntityNotFoundException("Contato não encontrado"));
	   return new ContatoDTO(ct);
	}
	
	@Transactional
	public ContatoDTO alterarContato(Long idcontato, Contato contato) {
		Contato ct = consultarContato(idcontato);
		BeanUtils.copyProperties(contato, ct, "id");
		return new ContatoDTO(repo.save(ct));
	}
	
	private  Contato consultarContato(Long idcontato) {
		   Optional<Contato> opcontato = repo.findById(idcontato);
		   Contato ct = opcontato.orElseThrow(() -> new EntityNotFoundException("Contato não encontrado"));
		   return ct;
	}
	
	@Transactional
	public void excluirContato(Long idcontato) {
		repo.deleteById(idcontato);	
	}
	
	@Transactional
	public List<ContatoDTO> consultarContatoPorEmail(String email) {
		return ContatoDTO.converteParaDTO(repo.findByEmail(email));
	}
	
	
}
