package br.edu.faeterj.petropolis.tcc.mealsdelivery.infrastructure.service.email

class EmailException(message: String, cause: Throwable?) : RuntimeException(message, cause) {
    constructor(message: String) : this(message, null)
}