package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception

class ProductImageNotFoundException(message: String) : EntityNotFoundException(message) {
    constructor(restaurantId: Long?, productId: Long?) : this(
        "Não existe um cadastro de foto do produto com código $productId para o restaurante de código $restaurantId"
    )
}