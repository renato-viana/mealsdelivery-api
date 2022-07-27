package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception

class CityNotFoundException(message: String) : EntityNotFoundException(message) {
    constructor(id: Long?) : this(String.format("Não existe um cadastro de cidade com código %d!", id))
}