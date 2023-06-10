package com.derso.vendas.dto;

import java.util.List;

import com.derso.vendas.validator.ListaNaoVazia;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record PedidoRequestDTO(
	@NotNull(message = "{campo.codigo-cliente.obrigatorio}")
	Long clienteId,
	
	// Não faz a validação nos objetos da lista sem o @Valid!
	@ListaNaoVazia(message = "{campo.items-pedido.obrigatorio}")
	@Valid
	List<ItemPedidoRequestDTO> itens) {
}
