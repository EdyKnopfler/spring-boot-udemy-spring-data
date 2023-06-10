package com.derso.vendas.validator;

import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ListaNaoVaziaValidator
		implements ConstraintValidator<ListaNaoVazia, List<?>> {

	@Override
	public boolean isValid(List<?> lista, ConstraintValidatorContext context) {
		return lista != null && !lista.isEmpty();
	}
	
	@Override
	public void initialize(ListaNaoVazia constraintAnnotation) {
		// TODO Auto-generated method stub
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

}
