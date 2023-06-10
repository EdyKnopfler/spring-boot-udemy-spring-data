package com.derso.vendas.dto;

public record ObjetoNaoEncontradoDTO(String tipo, long id) 
	implements GenericResponseDTO {

	public String status() {
		return "Não encontrado";
	}
	
}
