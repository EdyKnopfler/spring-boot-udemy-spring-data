package com.derso.vendas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.derso.vendas.domain.Pedido;

public record PedidoResponseDTO(
	long id,
	long clienteId,
	String nomeCliente,
	String cpf,
	LocalDate data,
	BigDecimal total,
	List<ItemPedidoResponseDTO> itens) {
	
	public static PedidoResponseDTO criarPara(Pedido pedido) {
		return new PedidoResponseDTO(
				pedido.getId(),
				pedido.getCliente().getId(),
				pedido.getCliente().getNome(),
				pedido.getCliente().getCpf(),
				pedido.getData(),
				pedido.getTotal(),
				pedido.getItens()
						.stream()
						.map(item -> ItemPedidoResponseDTO.criarPara(item))
						.toList()
		);
	}

}
