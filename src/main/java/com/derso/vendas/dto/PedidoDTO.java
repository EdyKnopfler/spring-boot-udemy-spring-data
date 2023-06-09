package com.derso.vendas.dto;

import java.util.List;

public record PedidoDTO(
	long clienteId,
	double totalEsperado,  // Esperamos receber o total do front-end para verificar discrepância
	List<ItemPedidoDTO> itens) {
}
