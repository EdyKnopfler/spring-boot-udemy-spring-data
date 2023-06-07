package com.derso.vendas.domain;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

@Entity
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Size(max=100)
	private String descricao;
	
	@DecimalMin(value = "0.0", inclusive = false, message = "Preço mínimo positivo")
	private BigDecimal preco;
	
	public Produto() {
		// O construtor default é importante caso façamos MERGE do objeto!
	}
	
	public Produto(String descricao, double preco) {
		setDescricao(descricao);
		setPreco(preco);
	}

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
	
	public void setPreco(double preco) {
		this.preco = new BigDecimal(preco);
	}
	
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
}
