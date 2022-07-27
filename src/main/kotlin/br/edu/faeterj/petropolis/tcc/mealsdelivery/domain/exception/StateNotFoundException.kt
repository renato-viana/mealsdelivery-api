package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception

class StateNotFoundException(message: String) : EntityNotFoundException(message) {
    constructor(id: Long?) : this(String.format("Não existe um cadastro de estado com código %d!", id))
}