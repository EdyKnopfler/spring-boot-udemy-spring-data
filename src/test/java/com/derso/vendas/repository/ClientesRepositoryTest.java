package com.derso.vendas.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.derso.vendas.domain.Cliente;
import com.derso.vendas.domain.ItemPedido;
import com.derso.vendas.domain.Pedido;
import com.derso.vendas.domain.Produto;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles({"test"})
public class ClientesRepositoryTest {
	
	@Autowired
	private ClientesRepository clientes;
	
	@Autowired
	private ProdutosRepository produtos;
	
	@Autowired
	private PedidosRepository pedidos;
	
	private long idCliente;
	
	@BeforeEach
	public void cenarioClienteComPedidos() {
		Cliente kania = new Cliente("Kânia", "Gato");
		clientes.saveAndFlush(kania);  // JpaRepository
		idCliente = kania.getId();
		
		Produto sache = new Produto("Sachê", 2.5);
		produtos.saveAndFlush(sache);
		
		for (int i = 0; i < 10; i++) {			
			Pedido pedido = new Pedido(kania, LocalDate.now());
			pedido.novoItem(new ItemPedido(sache, i));
			pedidos.saveAndFlush(pedido);
			
			Pedido salvo = pedidos.findById(pedido.getId()).get();
			System.out.println("SALVOU PEDIDO COM CLIENTE: "
					+ salvo.getCliente().getNome() + " - "
					+ salvo.getCliente().getId());
		}
	}
	
	@Test
	public void carregandoPedidosDoCliente() {
		// Consultando direto na tabela de pedidos: OK
		List<Pedido> pedidosDoKania = pedidos.findByClienteId(idCliente);
		assertEquals(10, pedidosDoKania.size());
		pedidosDoKania.stream().forEach(pedido -> System.out.println(pedido.getCliente().getId()));
	}
	
	@Test
	public void carregandoClienteComPedidos() {		
		Cliente kania = clientes.trazerComOsPedidos(idCliente);
		assertEquals("Kânia", kania.getNome());
		assertEquals("Gato", kania.getCpf());
		
		// https://medium.com/interleap/problems-with-hibernate-one-to-many-and-their-solutions-8f32af216b95
		// Problem: Saving data using 2 different repositories, and loading it within the object
		// will not work within the same session.
		// (se o @Transactional funcionasse aqui nos testes, não haveria esse problema)
		assertEquals(0, kania.getPedidos().size());
	}

}
