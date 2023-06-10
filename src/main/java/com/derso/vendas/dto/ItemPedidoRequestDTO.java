package com.derso.vendas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemPedidoRequestDTO(
	@NotNull(message = "{campo.codigo-produto.obrigatorio}")
	Long produtoId,
	
	@NotNull(message = "{campo.quantidade.obrigatorio}")
	@Min(value = 1, message = "{campo.quantidade.positivo}")
	Integer quantidade,
	
	Double totalEsperado) {
	// Esperamos receber o total do front-end para verificar discrepância
}
