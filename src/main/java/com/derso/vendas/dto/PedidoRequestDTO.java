package com.derso.vendas.dto;

import java.util.List;

public record PedidoRequestDTO(
	long clienteId,
	List<ItemPedidoRequestDTO> itens) {
}
