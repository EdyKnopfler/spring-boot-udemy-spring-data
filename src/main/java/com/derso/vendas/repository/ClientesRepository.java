package com.derso.vendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.derso.vendas.domain.Cliente;

public interface ClientesRepository extends JpaRepository<Cliente, Long> {
	
	List<Cliente> findByNomeLikeIgnoreCase(String nome);

}
