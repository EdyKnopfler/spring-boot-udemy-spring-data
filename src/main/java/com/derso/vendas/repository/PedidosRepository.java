package com.derso.vendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.derso.vendas.domain.Pedido;

public interface PedidosRepository extends JpaRepository<Pedido, Long> {
	
	List<Pedido> findByClienteId(long idCliente);

}
