package com.derso.vendas.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Pedido {
	
	private Long id;
	
	private Cliente cliente;
	private LocalDate data;
	private BigDecimal total;
	
	public Long getId() {
		return id;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public LocalDate getData() {
		return data;
	}
	
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public BigDecimal getTotal() {
		return total;
	}
	
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	

}
