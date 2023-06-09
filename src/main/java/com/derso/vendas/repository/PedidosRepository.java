package com.derso.vendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.derso.vendas.domain.Pedido;

public interface PedidosRepository extends JpaRepository<Pedido, Long> {
	
	List<Pedido> findByClienteId(long idCliente);

	/*
	 * Evitando ao máximo disparar queries para o banco de dados!
	 * 
	 * As configurações:
	 *  - spring.jpa.show-sql=true
	 *  - spring.jpa.properties.hibernate.format_sql=true
	 * Fazem realmente MUITA DIFERENÇA para otimizar queries.
	 */
	@Query(
		"SELECT p FROM Pedido p " +
		"JOIN FETCH p.cliente c " +
		"JOIN FETCH p.itens i " +
		"JOIN FETCH i.produto pr " +
		"WHERE c.id = :clienteId")
	List<Pedido> pedidosDoCliente(@Param("clienteId") long clienteId);

}
