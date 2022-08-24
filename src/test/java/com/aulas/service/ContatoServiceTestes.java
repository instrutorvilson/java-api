package com.aulas.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.aulas.entidades.Contato;
import com.aulas.repository.ContatoRepository;
import com.aulas.services.ContatoService;
import com.aulas.services.dto.ContatoDTO;

@ExtendWith(SpringExtension.class)
public class ContatoServiceTestes {
	private Long idExistente;
	private Long idNaoExistente;
	private Contato contatoValido;
	private Contato contatoInvalido;
	
	@BeforeEach
	void setup() {
		idExistente = 1l;
		idNaoExistente = 1000l;
		contatoValido = new Contato("maria", "maria@gmail.com", "(47)99999-9999");
		contatoInvalido = new Contato("maria", "mariagmail.com", "(47)99999-999");
		
		Mockito.doThrow(IllegalArgumentException.class).when(repository).save(contatoInvalido);
		Mockito.when(repository.save(contatoValido)).thenReturn(contatoValido);
		
		Mockito.doNothing().when(repository).deleteById(idExistente);
		Mockito.doThrow(EntityNotFoundException.class).when(repository).deleteById(idNaoExistente);
		
		Mockito.when(repository.findById(idExistente)).thenReturn(Optional.of(new Contato()));
		Mockito.doThrow(EntityNotFoundException.class).when(repository).findById(idNaoExistente);
	}
   
	@InjectMocks
	ContatoService service;
	
	@Mock
	ContatoRepository repository;
	
	
	public void retornaExcecaoQuandoSalvarSemSucesso() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> service.salvar(contatoInvalido));
		Mockito.verify(repository).save(contatoInvalido);
	}
	
	
	public void retornaContatoDTOQuandoSalvarComSucesso() {
		ContatoDTO contatoDTO = service.salvar(contatoValido);
		Assertions.assertNotNull(contatoDTO);
		Mockito.verify(repository).save(contatoValido);
	}
	
	
	public void retornaNadaAoExcluirComIdExistente() {
		Assertions.assertDoesNotThrow(() -> {
			service.excluirContato(idExistente);
		});
		Mockito.verify(repository,Mockito.times(1)).deleteById(idExistente);		
	}
	

	public void lancaEntidadeNaoEncontradaAoExcluirIdNaoExistente() {
		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.excluirContato(idNaoExistente);
		});
		Mockito.verify(repository,Mockito.times(1)).deleteById(idNaoExistente);
	}
	
	
	public void retornaContatoQuandoPesquisaPorIdExistente() {
		ContatoDTO ct = service.consultarContatoPorId(idExistente);
		Assertions.assertNotNull(ct);
		Mockito.verify(repository).findById(idExistente);
	}
	
	
	public void lancaEntidadeNaoEncontradaAoConsultarIdNaoExistente() {
		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.consultarContatoPorId(idNaoExistente);
		});
		Mockito.verify(repository,Mockito.times(1)).findById(idNaoExistente);
	}
}
