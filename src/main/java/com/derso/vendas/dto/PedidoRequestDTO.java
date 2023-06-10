package com.derso.vendas.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record PedidoRequestDTO(
	@NotNull(message = "O código do cliente é obrigatório")
	Long clienteId,
	
	// Não valida automaticamente os itens sem @Valid!
	@NotNull(message = "Os itens do pedido são obrigatórios")
	@Valid
	List<ItemPedidoRequestDTO> itens) {
}
