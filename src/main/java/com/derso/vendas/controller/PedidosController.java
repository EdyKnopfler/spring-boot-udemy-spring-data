package com.derso.vendas.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.derso.vendas.domain.Pedido;
import com.derso.vendas.dto.PedidoRequestDTO;
import com.derso.vendas.dto.PedidoResponseDTO;
import com.derso.vendas.dto.PedidoResponseOkDTO;
import com.derso.vendas.dto.ResumoPedidosProject;
import com.derso.vendas.dto.StatusPedidoRequestDTO;
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
	
	@GetMapping("/{id}")
	public PedidoResponseDTO porId(@PathVariable("id") long id) {
		return PedidoResponseDTO.criarPara(
				servico.porId(id).orElseThrow(
						() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
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
	
	@GetMapping("/resumo")
	public List<ResumoPedidosProject> resumo() {
		return servico.resumoPedidos();
	}
	
	// PATCH: atualização parcial (atualizar somente um campo)
	@PatchMapping("/{id}/status")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public PedidoResponseOkDTO atualizarStatus(
			@PathVariable("id") long id, 
			@RequestBody StatusPedidoRequestDTO novoStatus) {
		if (servico.atualizarStatus(id, novoStatus.status())) {
			return new PedidoResponseOkDTO("ok", id);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	private List<PedidoResponseDTO> formatarPedidos(List<Pedido> pedidos) {
		return pedidos
				.stream()
				.map(pedido -> PedidoResponseDTO.criarPara(pedido))
				.toList();
	}
	
}
