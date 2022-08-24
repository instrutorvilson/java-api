package com.aulas.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.aulas.entidades.Compromisso;
import com.aulas.repository.CompromissoRepository;
import com.aulas.services.CompromissoService;

@ExtendWith(SpringExtension.class)
public class CompromissoServiceTestes {
  private Compromisso compromissoValido;
  private Compromisso compromissoInvalido;
  
	
  @InjectMocks
  CompromissoService  service;
  
  @Mock
  CompromissoRepository repository;
  
  @BeforeEach
  void setup() {
	 compromissoValido = new Compromisso(); 
	 compromissoInvalido = new Compromisso(); 
	 Mockito.when(repository.save(compromissoValido)).thenReturn(compromissoValido);
	 Mockito.doThrow(IllegalArgumentException.class).when(repository).save(compromissoInvalido);
  }
  
  
  public void retornaCompromissoQuandoSalvaComSucesso() {
	  Assertions.assertNotNull(service.salvar(compromissoValido));
	  Mockito.verify(repository).save(compromissoValido);
  }
  
  
  public void lancaExcecaoQuandoSalvaSemSucesso() {
	  Assertions.assertThrows(IllegalArgumentException.class, () -> service.salvar(compromissoInvalido));
	  Mockito.verify(repository).save(compromissoInvalido);
  }
}
