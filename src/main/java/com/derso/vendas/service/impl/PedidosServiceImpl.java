package com.derso.vendas.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
			.orElseThrow(() -> new PedidosException(
					"Cliente não encontrado", Arrays.asList(clienteId)));
	}

	private Map<Long, Produto> buscarProdutos(PedidoDTO dadosPedido) throws PedidosException {
		Map<Long, Produto> produtos = produtosRepo
				.findAllById(
						dadosPedido.itens().stream().map(item -> item.produtoId()).toList())
				.stream()
				.collect(Collectors.toMap(produto -> produto.getId(), produto -> produto));
		
		if (produtos.size() < dadosPedido.itens().size()) {
			// Regrinhas de negócio por minha própria conta :)
			List<Long> naoEncontrados = dadosPedido.itens().stream()
				.map(dadosItem -> dadosItem.produtoId())
				.filter(produtoId -> !produtos.containsKey(produtoId))
				.toList();
			
			throw new PedidosException("Produto(s) não encontado(s): ", naoEncontrados);
		}
		
		return produtos;
	}
	
	private void popularItens(
			PedidoDTO dadosPedido, Map<Long, Produto> produtos, Pedido pedido) 
			throws PedidosException {
		
		List<Long> totaisNaoCorrespondem = new ArrayList<>();
		
		dadosPedido.itens().forEach(dadosItem -> {
			ItemPedido item = new ItemPedido(
					produtos.get(dadosItem.produtoId()), dadosItem.quantidade());
			
			System.out.println(BigDecimal.valueOf(dadosItem.totalEsperado()));
			System.out.println(item.getTotalItem());
			
			// BigDecimal.equals também compara a escala!
			// https://medium.com/beingabetterdeveloper/comparing-bigdecimal-s-with-different-scales-2901bf26538f
			if (BigDecimal.valueOf(dadosItem.totalEsperado()).compareTo(item.getTotalItem()) != 0) {
				totaisNaoCorrespondem.add(dadosItem.produtoId());
			}
			
			pedido.novoItem(item);
		});
		
		if (totaisNaoCorrespondem.size() > 0) {
			throw new PedidosException("Totais de itens não correspondem", totaisNaoCorrespondem);
		}
	}
	
}
