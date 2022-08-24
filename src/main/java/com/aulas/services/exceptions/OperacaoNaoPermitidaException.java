package com.aulas.services.exceptions;

public class OperacaoNaoPermitidaException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public OperacaoNaoPermitidaException(String msg) {
		super(msg);
	}
}
