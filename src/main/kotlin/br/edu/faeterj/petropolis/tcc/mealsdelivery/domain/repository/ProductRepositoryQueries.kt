package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.ProductImage

interface ProductRepositoryQueries {

    fun save(image: ProductImage): ProductImage?

    fun delete(image: ProductImage)

}
