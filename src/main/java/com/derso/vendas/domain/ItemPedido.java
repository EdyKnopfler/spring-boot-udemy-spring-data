package com.derso.vendas.domain;

public class ItemPedido {
	
	private Long id;
	
	private Pedido pedido;
	private Produto produto;
	private int quantidade;
	
	public Long getId() {
		return id;
	}
	
	public Pedido getPedido() {
		return pedido;
	}
	
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

}
