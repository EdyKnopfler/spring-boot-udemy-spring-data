package com.derso.vendas.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/*
 * VALIDAÇÃO CUSTOMIZADA!
 * 
 * @Constraint(validatedBy = <classe de validação>)
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ListaNaoVaziaValidator.class)
public @interface ListaNaoVazia {

	// Mínimo obrigatório
	String message() default "A lista não pode ser vazia";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
