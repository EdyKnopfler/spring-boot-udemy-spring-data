package com.derso.vendas.dto;

import java.util.List;

public record PedidoDTO(
	long clienteId,
	List<ItemPedidoDTO> itens) {
}
