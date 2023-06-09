package com.derso.vendas.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.derso.vendas.dto.PedidoResponseErrorDTO;
import com.derso.vendas.service.PedidosException;

@RestControllerAdvice
public class ApplicationControllerAdvice {
	
	@ExceptionHandler(PedidosException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public PedidoResponseErrorDTO handlePedidosException(PedidosException ex) {
		return new PedidoResponseErrorDTO("erro", ex.getMessage(), ex.getIds());
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public PedidoResponseErrorDTO falhaNoCorpoDaRequisicao(HttpMessageNotReadableException ex) {
		return new PedidoResponseErrorDTO("erro", "Problema no corpo da requisição", null);
	}

}
