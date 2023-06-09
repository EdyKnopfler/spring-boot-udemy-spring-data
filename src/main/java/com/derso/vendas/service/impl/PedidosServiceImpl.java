package com.derso.vendas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.derso.vendas.dto.PedidoDTO;
import com.derso.vendas.repository.PedidosRepository;
import com.derso.vendas.service.PedidosService;

@Service
public class PedidosServiceImpl implements PedidosService {
	
	private PedidosRepository pedidosRepo;
	
	@Autowired
	public PedidosServiceImpl(PedidosRepository pedidosRepo) {
		this.pedidosRepo = pedidosRepo;
	}

	@Override
	public void novoPedido(PedidoDTO dadosPedido) {
		// TODO antes de começar iremos fazer alguns ajustes
	}

}
