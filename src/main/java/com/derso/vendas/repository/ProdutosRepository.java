package com.derso.vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.derso.vendas.domain.Produto;

public interface ProdutosRepository extends JpaRepository<Produto, Long> {

}
