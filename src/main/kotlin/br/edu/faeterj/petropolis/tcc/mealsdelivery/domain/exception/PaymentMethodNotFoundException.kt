package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception

class PaymentMethodNotFoundException(message: String) : EntityNotFoundException(message) {
    constructor(id: Long?) : this("Não existe um cadastro de forma de pagamento com código $id!")
}