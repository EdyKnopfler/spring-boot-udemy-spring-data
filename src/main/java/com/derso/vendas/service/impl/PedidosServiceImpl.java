package com.derso.vendas.service.impl;

import static com.derso.vendas.service.PedidosException.Problema.CLIENTE_NAO_ENCONTRADO;
import static com.derso.vendas.service.PedidosException.Problema.PRODUTO_NAO_ENCONTRADO;
import static com.derso.vendas.service.PedidosException.Problema.TOTAL_ITEM_NAO_CORRESPONDE;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.derso.vendas.domain.Cliente;
import com.derso.vendas.domain.ItemPedido;
import com.derso.vendas.domain.Pedido;
import com.derso.vendas.domain.Produto;
import com.derso.vendas.dto.PedidoDTO;
import com.derso.vendas.repository.ClientesRepository;
import com.derso.vendas.repository.PedidosRepository;
import com.derso.vendas.repository.ProdutosRepository;
import com.derso.vendas.service.PedidosException;
import com.derso.vendas.service.PedidosService;

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
		Cliente cliente = buscarCliente(dadosPedido.clienteId());
		Map<Long, Produto> produtos = buscarProdutos(dadosPedido);
		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setData(LocalDate.now());
		popularItens(dadosPedido, produtos, pedido);
		pedidosRepo.save(pedido);
		return pedido;
	}

	/*
	 * Depois de uma temporada brincando de funcional em Python e JavaScript... :P
	 */

	private Cliente buscarCliente(long clienteId) throws PedidosException {
		return clientesRepo
				.findById(clienteId)
				.orElseThrow(() -> new PedidosException(CLIENTE_NAO_ENCONTRADO));
	}

	private Map<Long, Produto> buscarProdutos(PedidoDTO dadosPedido) throws PedidosException {
		Map<Long, Produto> produtos = produtosRepo
				.findAllById(
						dadosPedido.itens().stream().map(item -> item.produtoId()).toList())
				.stream()
				.collect(Collectors.toMap(produto -> produto.getId(), produto -> produto));
		
		if (produtos.size() < dadosPedido.itens().size()) {
			// Regrinhas de negócio por minha própria conta :)
			throw new PedidosException(PRODUTO_NAO_ENCONTRADO);
		}
		
		return produtos;
	}
	
	private void popularItens(PedidoDTO dadosPedido, Map<Long, Produto> produtos, Pedido pedido) {
		dadosPedido.itens().forEach(dadosItem -> {
			ItemPedido item = new ItemPedido(
					produtos.get(dadosItem.produtoId()), dadosItem.quantidade());
			
			if (Math.abs(dadosItem.totalEsperado() - item.getTotalItem().floatValue()) < 0.001) {
				throw new RuntimeException(new PedidosException(TOTAL_ITEM_NAO_CORRESPONDE));
			}
			
			pedido.novoItem(item);
		});
		
		if (Math.abs(pedido.getTotal().floatValue() - dadosPedido.totalEsperado()) < 0.001) {
			throw new RuntimeException(new PedidosException(TOTAL_ITEM_NAO_CORRESPONDE));
		}
	}

	
}
