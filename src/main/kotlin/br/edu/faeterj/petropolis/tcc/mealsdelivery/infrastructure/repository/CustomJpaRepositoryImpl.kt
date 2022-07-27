package br.edu.faeterj.petropolis.tcc.mealsdelivery.infrastructure.repository

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.CustomJpaRepository
import org.springframework.data.jpa.repository.support.JpaEntityInformation
import org.springframework.data.jpa.repository.support.SimpleJpaRepository
import javax.persistence.EntityManager

class CustomJpaRepositoryImpl<T, ID>(

    entityInformation: JpaEntityInformation<T, *>?,
    entityManager: EntityManager

) : SimpleJpaRepository<T, ID>(entityInformation as JpaEntityInformation<T, *>, entityManager),
    CustomJpaRepository<T, ID> {

    private val manager: EntityManager

    init {
        manager = entityManager
    }

    override fun detach(entity: T) {
        manager.detach(entity)
    }

}