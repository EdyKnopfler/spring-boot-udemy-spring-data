package com.derso.vendas.dto;

/*
 * Modelando DTOs como records.
 * 
 * A intenção é poder devolver subtipos diferentes. Todos eles têm a indicação de status.
 */

public interface GenericResponseDTO {
	
	String status();
	
}
