package com.derso.vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.derso.vendas.domain.Pedido;

public interface PedidosRepository extends JpaRepository<Pedido, Long> {

}
