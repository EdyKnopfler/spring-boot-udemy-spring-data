package com.derso.vendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.derso.vendas.domain.Cliente;

public interface ClientesRepository extends JpaRepository<Cliente, Long> {
	
	/*
	 * CONVENÇÃO:
	 * 
	 * findBy...: devolve um List<T> 
	 * findOneBy...: devolve um T (somente uma linha da tabela)
	 * findBy...Or...(..., ...): procura por mais de um campo (parâmetros na ordem)
	 * parâmetros String: ...Like, ...LikeIgnoreCase
	 * existsBy...: devolve um boolean
	 */
	
	List<Cliente> findByNomeLikeIgnoreCase(String nome);
	
	Cliente findOneByCpf(String cpf);
	
	// Customização do método
	@Query("SELECT c FROM Cliente c WHERE UPPER(c.nome) LIKE '%' || UPPER(:nome) || '%'")
	List<Cliente> encontrarPorNome(@Param("nome") String nome);
	
	@Query("DELETE FROM Cliente c WHERE c.cpf = :cpf")
	@Modifying  // int devolverá a quantidade excluída 
	int excluirPorCpf(@Param("cpf") String cpf);
	
	// FETCH
	// @OneToMany é LAZY por default
	@Query("SELECT c FROM Cliente c LEFT JOIN FETCH c.pedidos p WHERE c.id = :id")
	Cliente trazerComOsPedidos(@Param("id") long id);

}
