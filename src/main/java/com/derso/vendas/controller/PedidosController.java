package com.derso.vendas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.derso.vendas.domain.Pedido;
import com.derso.vendas.dto.PedidoDTO;
import com.derso.vendas.dto.PedidoResponseDTO;
import com.derso.vendas.dto.PedidoResponseErrorDTO;
import com.derso.vendas.dto.PedidoResponseOkDTO;
import com.derso.vendas.service.PedidosException;
import com.derso.vendas.service.PedidosService;

@RestController
@RequestMapping("/pedidos")
public class PedidosController {
	
	private PedidosService servico;

	@Autowired
	public PedidosController(PedidosService servico) {
		this.servico = servico;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<PedidoResponseDTO> novo(@RequestBody PedidoDTO dadosPedido) {
		try {
			Pedido pedido;
			pedido = servico.novoPedido(dadosPedido);
			return ResponseEntity.ok(new PedidoResponseOkDTO("ok", pedido.getId()));
		} catch (PedidosException e) {
			return ResponseEntity
				.badRequest().body(new PedidoResponseErrorDTO("erro", e.getMessage(), e.getIds()));
		}
	}
	
}
