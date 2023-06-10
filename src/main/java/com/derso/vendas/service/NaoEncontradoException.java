package com.derso.vendas.service;

public class NaoEncontradoException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String tipo;
	private long id;
	
	public NaoEncontradoException(String tipo, long id) {
		this.tipo = tipo;
		this.id = id;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public long getId() {
		return id;
	}

}
