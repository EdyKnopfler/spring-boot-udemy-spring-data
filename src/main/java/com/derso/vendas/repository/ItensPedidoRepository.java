package com.derso.vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.derso.vendas.domain.ItemPedido;

public interface ItensPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
