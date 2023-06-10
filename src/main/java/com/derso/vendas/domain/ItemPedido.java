package com.derso.vendas.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemPedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private Produto produto;
	
	@ManyToOne
	@JsonIgnore
	private Pedido pedido;
	
	private BigDecimal precoCorrente;
	
	private int quantidade;
	
	public ItemPedido() {
		// O construtor default é importante caso façamos MERGE do objeto!
	}
	
	public ItemPedido(Produto produto, int quantidade) {
		setProduto(produto);
		setQuantidade(quantidade);
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	public Pedido getPedido() {
		return pedido;
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
