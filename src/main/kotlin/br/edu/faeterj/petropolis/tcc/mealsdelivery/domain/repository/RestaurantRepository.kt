package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Restaurant
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.util.*

@Repository
interface RestaurantRepository : CustomJpaRepository<Restaurant, Long>,
    RestaurantRepositoryQueries, JpaSpecificationExecutor<Restaurant?> {

    @Query("FROM Restaurant r JOIN r.cuisine")
    override fun findAll(): List<Restaurant?>

    fun queryByDeliveryFeeBetween(initialFee: BigDecimal, finalFee: BigDecimal): List<Restaurant?>

    @Query("FROM Restaurant WHERE nome LIKE %:nome% and cuisine.id = :id")
    fun searchByName(nome: String, @Param("id") cuisineId: Long): List<Restaurant?>

    fun findFirstByNameContaining(nome: String): Optional<Restaurant?>

    fun findTop2ByNameContaining(nome: String): List<Restaurant?>

    fun countByCuisineId(cuisineId: Long): Int

    fun isResponsible(restaurantId: Long?, userId: Long?): Boolean

}