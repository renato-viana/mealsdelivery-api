package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.EntityInUseException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.RestaurantNotFoundException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.*
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.RestaurantRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.function.Consumer

@Service
class RestaurantService(

    @Autowired
    var restaurantRepository: RestaurantRepository,

    @Autowired
    var cuisineService: CuisineService,

    @Autowired
    var cityService: CityService,

    @Autowired
    var paymentMethodService: PaymentMethodService,

    @Autowired
    var userService: UserService,

    private val MSG_RESTAURANT_IN_USE: String = "Restaurante de código %d não pode ser removido, pois está em uso!"

) {

    @Transactional
    fun save(restaurant: Restaurant): Restaurant {
        val cuisineId: Long = restaurant.cuisine.id!!
        val cityId: Long? = restaurant.address.city?.id
        val cuisine: Cuisine = cuisineService.fetchOrFail(cuisineId)
        val city: City = cityService.fetchOrFail(cityId)
        restaurant.cuisine = cuisine
        restaurant.address.city = city
        return restaurantRepository.save(restaurant)
    }

    @Transactional
    fun delete(restaurantId: Long) {
        try {
            restaurantRepository.deleteById(restaurantId)
            restaurantRepository.flush()
        } catch (e: EmptyResultDataAccessException) {
            throw RestaurantNotFoundException(restaurantId)
        } catch (e: DataIntegrityViolationException) {
            throw EntityInUseException(String.format(MSG_RESTAURANT_IN_USE, restaurantId))
        }
    }

    @Transactional
    fun activate(restaurantId: Long?) {
        val restaurant: Restaurant = fetchOrFail(restaurantId)
        restaurant.activate()
    }

    @Transactional
    fun inactivate(restaurantId: Long?) {
        val restaurant: Restaurant = fetchOrFail(restaurantId)
        restaurant.inactivate()
    }

    @Transactional
    fun activate(restaurantIds: List<Long>) {
        restaurantIds.forEach(Consumer { restaurantId: Long -> this.activate(restaurantId) })
    }

    @Transactional
    fun inactivate(restaurantIds: List<Long>) {
        restaurantIds.forEach(Consumer { restaurantId: Long -> this.inactivate(restaurantId) })
    }

    @Transactional
    fun open(restaurantId: Long?) {
        val restaurant: Restaurant = fetchOrFail(restaurantId)
        restaurant.open()
    }

    @Transactional
    fun close(restaurantId: Long?) {
        val restaurant: Restaurant = fetchOrFail(restaurantId)
        restaurant.close()
    }

    @Transactional
    fun disassociatePaymentMethod(restaurantId: Long?, paymentMethodId: Long?) {
        val restaurant: Restaurant = fetchOrFail(restaurantId)
        val paymentMethod: PaymentMethod = paymentMethodService.fetchOrFail(paymentMethodId)
        restaurant.removePaymentMethod(paymentMethod)
    }

    @Transactional
    fun associatePaymentMethod(restaurantId: Long?, paymentMethodId: Long?) {
        val restaurant: Restaurant = fetchOrFail(restaurantId)
        val paymentMethod: PaymentMethod = paymentMethodService.fetchOrFail(paymentMethodId)
        restaurant.addPaymentMethod(paymentMethod)
    }

    @Transactional
    fun disassociateResponsible(restaurantId: Long?, userId: Long?) {
        val restaurant: Restaurant = fetchOrFail(restaurantId)
        val user: User = userService.fetchOrFail(userId)
        restaurant.removeResponsible(user)
    }

    @Transactional
    fun associateResponsible(restaurantId: Long?, userId: Long?) {
        val restaurant: Restaurant = fetchOrFail(restaurantId)
        val user: User = userService.fetchOrFail(userId)

        restaurant.addResponsible(user)
    }

    fun fetchOrFail(restaurantId: Long?): Restaurant =
        restaurantRepository.findByIdOrNull(restaurantId) ?: throw RestaurantNotFoundException(restaurantId)

}