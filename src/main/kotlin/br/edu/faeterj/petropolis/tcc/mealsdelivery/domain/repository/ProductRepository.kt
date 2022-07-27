package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Product
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.ProductImage
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Restaurant
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long>, ProductRepositoryQueries {

    @Query("FROM Product WHERE restaurant.id = :restaurant and id = :Product")
    fun findById(@Param("restaurant") restaurantId: Long?, @Param("Product") ProductId: Long?): Product?

    fun findByRestaurant(restaurant: Restaurant): List<Product?>

    @Query("FROM Product p WHERE p.active = true and p.restaurant = :restaurant")
    fun findActivesByRestaurant(restaurant: Restaurant): List<Product?>

    @Query(
        "SELECT f FROM ProductImage f JOIN f.product p WHERE" +
                " p.restaurant.id = :restaurantId and f.product.id=:productId"
    )
    fun findImageById(restaurantId: Long?, productId: Long?): ProductImage?

}