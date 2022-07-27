package br.edu.faeterj.petropolis.tcc.mealsdelivery.infrastructure.service.report

class ReportException(message: String, cause: Throwable?) : RuntimeException(message, cause) {
    constructor(message: String) : this(message, null)
}