package com.derso.vendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

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

}
