package com.derso.vendas.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.derso.vendas.domain.Pedido;
import com.derso.vendas.dto.PedidoRequestDTO;
import com.derso.vendas.dto.PedidoResponseDTO;
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
	public ResponseEntity<PedidoResponseOkDTO> novo(
			@RequestBody PedidoRequestDTO dadosPedido) throws PedidosException {
		Pedido pedido = servico.novoPedido(dadosPedido);
		return ResponseEntity.ok(new PedidoResponseOkDTO("ok", pedido.getId()));
	}
	
	@GetMapping("/do-cliente/{clienteId}")
	public List<PedidoResponseDTO> pedidosDoCliente(@PathVariable("clienteId") long clienteId) {
		return formatarPedidos(servico.pedidosDoCliente(clienteId));
	}
	
	@GetMapping("/de/{inicio}/ate/{fim}")
	public List<PedidoResponseDTO> pedidosDoPeriodo(
			@PathVariable("inicio") LocalDate inicio,
			@PathVariable("fim") LocalDate fim,
			@RequestParam(name="pagina", required=false) Integer pagina) {
		pagina = pagina != null ? pagina - 1 : 0;
		return formatarPedidos(servico.pedidosDoPeriodo(inicio, fim, pagina));
	}
	
	private List<PedidoResponseDTO> formatarPedidos(List<Pedido> pedidos) {
		return pedidos
				.stream()
				.map(pedido -> PedidoResponseDTO.criarPara(pedido))
				.toList();
	}
	
}
