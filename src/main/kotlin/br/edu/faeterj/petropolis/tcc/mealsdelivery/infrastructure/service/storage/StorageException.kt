package br.edu.faeterj.petropolis.tcc.mealsdelivery.infrastructure.service.storage

open class StorageException(message: String, cause: Throwable?) : RuntimeException(message, cause) {
    constructor(message: String) : this(message, null)
}