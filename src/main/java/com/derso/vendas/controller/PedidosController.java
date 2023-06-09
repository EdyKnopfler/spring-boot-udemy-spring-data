package com.derso.vendas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.derso.vendas.domain.Pedido;
import com.derso.vendas.dto.PedidoRequestDTO;
import com.derso.vendas.dto.PedidoResponseDTO;
import com.derso.vendas.dto.GenericResponseDTO;
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
	public ResponseEntity<GenericResponseDTO> novo(@RequestBody PedidoRequestDTO dadosPedido) throws PedidosException {
		Pedido pedido = servico.novoPedido(dadosPedido);
		return ResponseEntity.ok(new PedidoResponseOkDTO("ok", pedido.getId()));
	}
	
	@GetMapping("/do-cliente/{clienteId}")
	public List<PedidoResponseDTO> pedidosDoCliente(@PathVariable("clienteId") long clienteId) {
		return servico
				.pedidosDoCliente(clienteId)
				.stream()
				.map(pedido -> PedidoResponseDTO.criarPara(pedido))
				.toList();
	}
	
}
