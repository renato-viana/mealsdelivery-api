package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception

class ProductNotFoundException(message: String) : EntityNotFoundException(message) {

    constructor(restaurantId: Long?, productId: Long?) : this(
        "Não existe um cadastro de produto com código $productId para o restaurante de código $restaurantId"
    )

}