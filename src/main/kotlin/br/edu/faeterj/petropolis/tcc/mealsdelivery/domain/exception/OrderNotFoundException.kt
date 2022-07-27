package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception

class OrderNotFoundException(code: String) : EntityNotFoundException(code) {
    init {
        String.format("Não existe um pedido com código %s.", code)
    }
}