package com.derso.vendas.dto;

import java.util.List;

// String status obrigatório pela implementação da interface
public record PedidoResponseErrorDTO(
	String status, 
	String problema,
	List<Long> ids) 
	implements GenericResponseDTO {
}
