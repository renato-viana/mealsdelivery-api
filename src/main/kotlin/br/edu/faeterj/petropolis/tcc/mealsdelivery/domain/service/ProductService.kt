package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.ProductNotFoundException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Product
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(

    @Autowired
    private val productRepository: ProductRepository

) {

    @Transactional
    fun save(product: Product): Product = productRepository.save(product)

    fun fetchOrFail(restaurantId: Long?, productId: Long?): Product =
        productRepository.findById(restaurantId, productId) ?: throw ProductNotFoundException(restaurantId, productId)

}