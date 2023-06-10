package com.derso.vendas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemPedidoRequestDTO(
	@NotNull(message = "O código do produto é obrigatório")
	Long produtoId,
	
	@NotNull(message = "A quantidade é obrigatória")
	@Min(value = 1, message = "Quantidade mínima: 1")
	Integer quantidade,
	
	Double totalEsperado) {
	// Esperamos receber o total do front-end para verificar discrepância
}
