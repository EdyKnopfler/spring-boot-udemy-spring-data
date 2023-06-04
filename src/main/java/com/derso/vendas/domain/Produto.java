package com.derso.vendas.domain;

import java.math.BigDecimal;

public class Produto {
	
	private Long id;
	
	private String descricao;
	private BigDecimal preco;
	
	public Long getId() {
		return id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public BigDecimal getPreco() {
		return preco;
	}
	
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
}
