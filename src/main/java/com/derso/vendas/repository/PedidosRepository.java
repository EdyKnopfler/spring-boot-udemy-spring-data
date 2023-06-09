package com.derso.vendas.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.derso.vendas.domain.Pedido;
import com.derso.vendas.dto.ResumoPedidosProject;

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

	/*
	 * O findByDataBetween(inicio, fim, pageable) estava fazendo N+1 mesmo com
	 * carregamento EAGER dos itens. Bora otimizar sapôrra.
	 * 
	 * Mantemos o LAZY na entidade supondo que há uma regra que apenas lista os pedidos
	 * (por ex., total do mês).
	 */
	@Query(
		"SELECT p FROM Pedido p " +
		"JOIN FETCH p.cliente c " +
		"LEFT JOIN FETCH p.itens i " +
		"JOIN FETCH i.produto pr " +
		"WHERE p.data BETWEEN :inicio AND :fim")
	Page<Pedido> pedidosDoPeriodo(
			@Param("inicio") LocalDate inicio, 
			@Param("fim") LocalDate fim, 
			Pageable pageable);
	
	// Query com projeção: não entendeu "p.data" como "data"
	@Query(
		"SELECT p.data AS data, SUM(p.total) AS total " +
		"FROM Pedido p " +
		"WHERE p.status = 'REALIZADO'")
	public List<ResumoPedidosProject> resumoPedidos();
	
	@Query("UPDATE Pedido p SET p.status = :status WHERE p.id = :id")
	@Modifying
	public int mudarStatus(@Param("id") long id, @Param("status") String status);

}
