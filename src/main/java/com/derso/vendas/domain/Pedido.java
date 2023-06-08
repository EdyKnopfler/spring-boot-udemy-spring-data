package com.derso.vendas.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.DecimalMin;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private Cliente cliente;
	
	private LocalDate data;
	
	@OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
	private List<ItemPedido> itens = new ArrayList<>();
	
	@DecimalMin(value = "0.0", inclusive = false, message = "Total mínimo positivo")
	private BigDecimal total = new BigDecimal("0.0");
	
	public Pedido() {
		// O construtor default é importante caso façamos MERGE do objeto!
	}
	
	public Pedido(Cliente cliente, LocalDate data) {
		setCliente(cliente);
		setData(data);
	}

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
	
	public void novoItem(ItemPedido item) {
		itens.add(item);
		total = total.add(item.getTotalItem());
	}
	
	public void removerItem(int posicao) {
		ItemPedido removido = itens.remove(posicao);
		total = total.subtract(removido.getTotalItem());
	}

	public List<ItemPedido> getItens() {
		return Collections.unmodifiableList(itens);
	}
	
}
