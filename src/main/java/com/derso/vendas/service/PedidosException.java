package com.derso.vendas.service;

import java.util.List;

public class PedidosException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private List<Long> ids;

	public PedidosException(String problema, List<Long> ids) {
		super(problema);
		this.ids = ids;
	}

	public List<Long> getIds() {
		return this.ids;
	}

}
