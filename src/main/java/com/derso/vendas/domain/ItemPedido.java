package com.derso.vendas.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

@Entity
public class ItemPedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private Produto produto;
	
	@DecimalMin(value = "0.0", inclusive = false, message = "Preço mínimo positivo")
	private BigDecimal precoCorrente;
	
	@Min(1)
	private int quantidade;
	
	public Long getId() {
		return id;
	}
	
	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
		this.precoCorrente = produto.getPreco();
	}
	
	public BigDecimal getPrecoCorrente() {
		return this.precoCorrente;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public BigDecimal getTotalItem() {
		return precoCorrente.multiply(new BigDecimal(quantidade));
	}

}
