package com.derso.vendas.dto;

// String status obrigatório pela implementação da interface
public record PedidoResponseErrorDTO(String status, String problema) implements PedidoResponseDTO {
}
