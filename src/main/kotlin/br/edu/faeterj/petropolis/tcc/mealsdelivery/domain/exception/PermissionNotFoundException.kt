package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception

class PermissionNotFoundException(message: String) : EntityNotFoundException(message) {
    constructor(id: Long?) : this(String.format("Não existe um cadastro de permissão com código %d!", id))
}