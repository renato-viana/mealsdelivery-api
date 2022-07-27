package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller.OrderController
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.helper.ResourceLinkHelper
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.OrderModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.Security
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Order
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.stereotype.Component

@Component
class OrderModelResponseAssembler(

    @Autowired
    private val modelMapper: ModelMapper,

    @Autowired
    private val resourceLinkHelper: ResourceLinkHelper,

    @Autowired
    private val security: Security

) : RepresentationModelAssemblerSupport<Order, OrderModelResponse>(
    OrderController::class.java, OrderModelResponse::class.java
) {

    override fun toModel(order: Order): OrderModelResponse {
        val orderModelResponse: OrderModelResponse = createModelWithId(order.code, order)

        modelMapper.map(order, orderModelResponse)

        if (security.canSearchOrders()) {
            orderModelResponse.add(resourceLinkHelper.linkToOrders("orders"))
        }

        if (security.canManageOrders(order.code)) {
            if (order.canBeConfirmed()) {
                orderModelResponse.add(resourceLinkHelper.linkToOrderConfirmation(order.code, "confirmed"))
            }

            if (order.canBeDelivered()) {
                orderModelResponse.add(resourceLinkHelper.linkToOrderDelivery(order.code, "delivered"))
            }

            if (order.canBeCancelled()) {
                orderModelResponse.add(resourceLinkHelper.linkToOrderCancellation(order.code, "cancelled"))
            }
        }

        if (security.canConsultRestaurants()) {
            orderModelResponse.restaurant.add(
                resourceLinkHelper.linkToRestaurant(order.restaurant.id)
            )
        }

        if (security.canConsultUsersRolesPermissions()) {
            orderModelResponse
                .customer.add(resourceLinkHelper.linkToUser(order.customer.id))
        }

        if (security.canConsultPaymentMethods()) {
            orderModelResponse.paymentMethod.add(resourceLinkHelper.linkToPaymentMethod(order.paymentMethod.id))
        }

        if (security.canConsultCities()) {
            orderModelResponse.deliveryAddress.city?.add(
                resourceLinkHelper.linkToCity(order.deliveryAddress.city?.id)
            )
        }

        // Quem pode consultar restaurantes, também pode consultar os produtos dos restaurantes
        if (security.canConsultRestaurants()) {
            orderModelResponse
                .items.forEach { item ->
                    item.add(
                        resourceLinkHelper
                            .linkToProduct(orderModelResponse.restaurant.id, item.productId, "products")
                    )
                }
        }

        return orderModelResponse
    }

}