package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception

class RoleNotFoundException(message: String) : EntityNotFoundException(message) {
    constructor(id: Long?) : this(String.format("Não existe um cadastro de função com código %d!", id))
}