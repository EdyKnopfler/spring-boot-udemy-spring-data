package com.derso.vendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.derso.vendas.domain.Pedido;

public interface PedidosRepository extends JpaRepository<Pedido, Long> {
	
	List<Pedido> findByClienteId(long idCliente);

	@Query("SELECT p FROM Pedido p JOIN FETCH ItemPedido i WHERE p.cliente.id = :clienteId")
	List<Pedido> pedidosDoCliente(@Param("clienteId") long clienteId);

}
