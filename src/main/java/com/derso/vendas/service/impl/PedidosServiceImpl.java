package com.derso.vendas.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.derso.vendas.domain.Cliente;
import com.derso.vendas.domain.Pedido;
import com.derso.vendas.dto.PedidoDTO;
import com.derso.vendas.repository.ClientesRepository;
import com.derso.vendas.repository.PedidosRepository;
import com.derso.vendas.repository.ProdutosRepository;
import com.derso.vendas.service.PedidosException;
import com.derso.vendas.service.PedidosService;

import static com.derso.vendas.service.PedidosException.Problema.*;

@Service
public class PedidosServiceImpl implements PedidosService {
	
	private PedidosRepository pedidosRepo;
	private ClientesRepository clientesRepo;
	private ProdutosRepository produtosRepo;
	
	@Autowired
	public PedidosServiceImpl(
			PedidosRepository pedidosRepo,
			ClientesRepository clientesRepo,
			ProdutosRepository produtosRepo) {
		this.pedidosRepo = pedidosRepo;
		this.clientesRepo = clientesRepo;
		this.produtosRepo = produtosRepo;
	}

	@Override
	@Transactional
	public Pedido novoPedido(PedidoDTO dadosPedido) throws PedidosException {
		Cliente cliente = clientesRepo
				.findById(dadosPedido.clienteId())
				.orElseThrow(() -> new PedidosException(CLIENTE_NAO_ENCONTRADO));
		
		Pedido pedido = new Pedido();
		pedido.setData(LocalDate.now());
		
		return pedido;
	}

}
