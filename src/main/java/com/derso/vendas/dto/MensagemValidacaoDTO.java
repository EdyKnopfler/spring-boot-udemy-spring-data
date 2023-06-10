package com.derso.vendas.dto;

import java.util.List;

public record MensagemValidacaoDTO(String status, List<String> erros) 
	implements GenericResponseDTO {

}
