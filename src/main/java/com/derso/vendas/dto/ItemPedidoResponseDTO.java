package com.derso.vendas.dto;

import java.math.BigDecimal;

import com.derso.vendas.domain.ItemPedido;

public record ItemPedidoResponseDTO(
	long id,
	long produtoId,
	String descricao,
	int quantidade,
	BigDecimal precoCorrente,
	BigDecimal totalItem) {

	public static ItemPedidoResponseDTO criarPara(ItemPedido item) {
		return new ItemPedidoResponseDTO(
				item.getId(),
				item.getProduto().getId(),
				item.getProduto().getDescricao(),
				item.getQuantidade(),
				item.getPrecoCorrente(),
				item.getTotalItem());
	}

}
