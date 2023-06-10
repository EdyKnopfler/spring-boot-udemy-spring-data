package com.derso.vendas.controller.advice;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.derso.vendas.dto.MensagemValidacaoDTO;
import com.derso.vendas.dto.ObjetoNaoEncontradoDTO;
import com.derso.vendas.dto.PedidoResponseErrorDTO;
import com.derso.vendas.service.NaoEncontradoException;
import com.derso.vendas.service.PedidosException;

/*
 * TRATADORES DE ERRO
 */

@RestControllerAdvice
public class ApplicationControllerAdvice {
	
	// Exceções de negócio
	@ExceptionHandler(PedidosException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public PedidoResponseErrorDTO handlePedidosException(PedidosException ex) {
		return new PedidoResponseErrorDTO("erro", ex.getMessage(), ex.getIds());
	}
	
	@ExceptionHandler(NaoEncontradoException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ObjetoNaoEncontradoDTO objetoNaoEncontrado(NaoEncontradoException ex) {
		return new ObjetoNaoEncontradoDTO(ex.getTipo(), ex.getId());
	}
	
	// Erro na transformação do JSON para o objeto @ResponseBody
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public PedidoResponseErrorDTO falhaNoCorpoDaRequisicao(HttpMessageNotReadableException ex) {
		return new PedidoResponseErrorDTO("erro", "Problema no corpo da requisição", null);
	}
	
	// Erro nos @Valid dos objetos
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public MensagemValidacaoDTO falhaDeValidacao(MethodArgumentNotValidException ex) {
		// getDefaultMessage(): mensagem definida na anotação do atributo na entidade
		List<String> erros = ex.getAllErrors().stream().map(
				error -> error.getDefaultMessage()).toList();
		return new MensagemValidacaoDTO("erro", erros); 
	}

}
