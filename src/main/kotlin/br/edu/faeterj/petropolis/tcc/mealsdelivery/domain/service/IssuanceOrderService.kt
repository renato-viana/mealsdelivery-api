package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.BusinessException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.OrderNotFoundException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.*
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class IssuanceOrderService(

    @Autowired
    var restaurantService: RestaurantService,

    @Autowired
    var cityService: CityService,

    @Autowired
    var userService: UserService,

    @Autowired
    var productService: ProductService,

    @Autowired
    var paymentMethodService: PaymentMethodService,

    @Autowired
    var orderRepository: OrderRepository

) {

    @Transactional
    fun issue(order: Order): Order {
        validateOrder(order)
        validateItems(order)

        order.deliveryFee = order.restaurant.deliveryFee!!
        order.calculateTotalPrice()

        return orderRepository.save(order)
    }

    private fun validateOrder(order: Order) {
        val city: City = cityService.fetchOrFail(order.deliveryAddress.city?.id)
        val customer: User = userService.fetchOrFail(order.customer.id!!)
        val restaurant: Restaurant = restaurantService.fetchOrFail(order.restaurant.id)
        val paymentMethod: PaymentMethod = paymentMethodService.fetchOrFail(order.paymentMethod.id)

        order.deliveryAddress.city = city
        order.customer = customer
        order.restaurant = restaurant
        order.paymentMethod = paymentMethod

        if (restaurant.doesNotAcceptPaymentMethod(paymentMethod)) {
            throw BusinessException(
                "Forma de pagamento '${paymentMethod.description}' não é aceita por esse restaurante."
            )
        }
    }

    private fun validateItems(order: Order) {
        order.items.forEach { item ->
            val product: Product = productService.fetchOrFail(order.restaurant.id, item.product.id)
            item.order = order
            item.product = product
            item.unitPrice = product.price
        }
    }

    fun fetchOrFail(code: String): Order = orderRepository.findByCode(code) ?: throw OrderNotFoundException(code)

}