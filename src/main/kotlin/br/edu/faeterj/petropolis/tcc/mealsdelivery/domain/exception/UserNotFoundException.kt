package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception

class UserNotFoundException(message: String) : EntityNotFoundException(message) {
    constructor(id: Long?) : this("Não existe um cadastro de usuário com código $id!")
}