package com.derso.vendas.service;

import java.time.LocalDate;
import java.util.List;

import com.derso.vendas.domain.Pedido;
import com.derso.vendas.domain.Pedido.StatusPedido;
import com.derso.vendas.dto.PedidoRequestDTO;
import com.derso.vendas.dto.ResumoPedidosProject;

/*
 * "Pedidos" é mais complexo e agregará mais repositórios.
 * O serviço ficará entre o controller e os repositórios
 */

public interface PedidosService {

	Pedido novoPedido(PedidoRequestDTO dadosPedido) throws PedidosException;

	List<Pedido> pedidosDoCliente(long clienteId);

	List<Pedido> pedidosDoPeriodo(LocalDate inicio, LocalDate fim, int pagina);

	Pedido porId(long id) throws NaoEncontradoException;
	
	List<ResumoPedidosProject> resumoPedidos();
	
	void atualizarStatus(long id, StatusPedido novoStatus) throws NaoEncontradoException;

}
