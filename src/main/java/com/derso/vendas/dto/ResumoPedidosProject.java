package com.derso.vendas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/*
 * Projeção: relembrando aula da Alura
 * 
 * A interface define os getters de acordo com os atributos
 * 
 * https://github.com/EdyKnopfler/alura-spring-data/blob/main/src/main/java/br/com/pip/springdata/repository/FuncionarioRepository.java
 * https://github.com/EdyKnopfler/alura-spring-data/blob/main/src/main/java/br/com/pip/springdata/orm/FuncionarioProjecao.java
 */

public interface ResumoPedidosProject {
	
	LocalDate getData();
	BigDecimal getTotal();

}
