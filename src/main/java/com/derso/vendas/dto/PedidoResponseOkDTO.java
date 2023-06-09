package com.derso.vendas.dto;

// String status obrigatório pela implementação da interface
public record PedidoResponseOkDTO(String status, long id) implements PedidoResponseDTO {
}
