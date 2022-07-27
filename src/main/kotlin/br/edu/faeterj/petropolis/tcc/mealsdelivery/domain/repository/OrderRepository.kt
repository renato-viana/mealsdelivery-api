package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Order
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : CustomJpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    fun findByCode(code: String): Order?

    @Query("FROM Order o JOIN FETCH o.customer JOIN FETCH o.restaurant r JOIN FETCH r.cuisine")
    override fun findAll(): List<Order?>

    fun isOrderManagedBy(orderCode: String, userId: Long): Boolean

}