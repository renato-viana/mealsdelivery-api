package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception

class CuisineNotFoundException(message: String) : EntityNotFoundException(message) {
    constructor(id: Long) : this(String.format("Não existe um cadastro de cozinha com código %d!", id))
}