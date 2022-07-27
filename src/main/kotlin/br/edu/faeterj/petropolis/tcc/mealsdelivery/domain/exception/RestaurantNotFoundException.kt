package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception

class RestaurantNotFoundException(message: String) : EntityNotFoundException(message) {
    constructor(id: Long?) : this("Não existe um cadastro de restaurante com código $id!")
}