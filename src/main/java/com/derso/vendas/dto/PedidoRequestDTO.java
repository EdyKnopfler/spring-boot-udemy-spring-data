package com.derso.vendas.dto;

import java.util.List;

import com.derso.vendas.validator.ListaNaoVazia;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record PedidoRequestDTO(
	@NotNull(message = "O código do cliente é obrigatório")
	Long clienteId,
	
	// Não faz a validação nos objetos da lista sem o @Valid!
	@ListaNaoVazia(message = "Os itens do pedido são obrigatórios")
	@Valid
	List<ItemPedidoRequestDTO> itens) {
}
