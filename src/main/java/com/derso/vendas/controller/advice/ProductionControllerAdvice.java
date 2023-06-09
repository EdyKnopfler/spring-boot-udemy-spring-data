package com.derso.vendas.controller.advice;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Profile("production")
public class ProductionControllerAdvice {
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String erroNaoTratadoEmProducao(Exception ex) {
		// Logando
		System.out.println("Ocorreu um erro não tratado!");
		ex.printStackTrace();
		
		// Devolvendo uma informação amigável e escondendo detalhes de implementação
		return "Erro interno do servidor :(";
	}

}
