package br.edu.faeterj.petropolis.tcc.mealsdelivery.infrastructure.repository

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.ProductImage
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.ProductRepositoryQueries
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class ProductRepositoryImpl(

    @PersistenceContext
    private val manager: EntityManager

) : ProductRepositoryQueries {

    @Transactional
    override fun save(image: ProductImage): ProductImage? {
        return manager.merge(image)
    }

    @Transactional
    override fun delete(image: ProductImage) {
        manager.remove(image)
    }

}