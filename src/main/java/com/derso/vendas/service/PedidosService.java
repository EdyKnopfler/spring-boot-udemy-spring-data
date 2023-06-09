package com.derso.vendas.service;

import com.derso.vendas.dto.PedidoDTO;

/*
 * "Pedidos" é mais complexo e agregará mais repositórios.
 * O serviço ficará entre o controller e os repositórios
 */

public interface PedidosService {

	void novoPedido(PedidoDTO dadosPedido);

}
