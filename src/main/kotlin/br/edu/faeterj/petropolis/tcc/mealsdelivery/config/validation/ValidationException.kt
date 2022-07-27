package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.validation

import org.springframework.validation.BindingResult

class ValidationException(var bindingResult: BindingResult) : RuntimeException() {
}