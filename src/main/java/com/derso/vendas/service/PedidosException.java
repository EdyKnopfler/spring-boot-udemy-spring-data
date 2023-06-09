package com.derso.vendas.service;

public class PedidosException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public enum Problema {
		CLIENTE_NAO_ENCONTRADO,
		PRODUTO_NAO_ENCONTRADO,
		TOTAL_ITEM_NAO_CORRESPONDE,
		TOTAL_PEDIDO_NAO_CORRESPONDE
	}
	
	private Problema problema;
	
	public PedidosException(Problema problema) {
		this.problema = problema;
	}
	
	@Override
	public String toString() {
		return problema.toString();
	}

	public Problema getProblema() {
		return problema;
	}

}
