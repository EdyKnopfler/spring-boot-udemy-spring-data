package com.derso.vendas.dto;

public record ItemPedidoDTO(
	long produtoId,
	int quantidade,
	double totalEsperado) {
	// Esperamos receber o total do front-end para verificar discrepância
}
