package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception

open class BusinessException(message: String?, cause: Throwable?) : RuntimeException(message, cause) {
    constructor(message: String?) : this(message, null)
}