package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller.OrderController
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.helper.ResourceLinkHelper
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.OrderSummaryModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.Security
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Order
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.stereotype.Component

@Component
class OrderSummaryModelResponseAssembler(

    @Autowired
    private val modelMapper: ModelMapper,

    @Autowired
    private val resourceLinkHelper: ResourceLinkHelper,

    @Autowired
    private val security: Security

) : RepresentationModelAssemblerSupport<Order, OrderSummaryModelResponse>(
    OrderController::class.java, OrderSummaryModelResponse::class.java
) {

    override fun toModel(order: Order): OrderSummaryModelResponse {
        val orderModelResponse: OrderSummaryModelResponse = createModelWithId(order.code, order)

        modelMapper.map(order, orderModelResponse)

        if (security.canSearchOrders()) {
            orderModelResponse.add(resourceLinkHelper.linkToOrders("orders"))
        }

        if (security.canConsultRestaurants()) {
            orderModelResponse.restaurant.add(resourceLinkHelper.linkToRestaurant(order.restaurant.id))
        }

        if (security.canConsultUsersRolesPermissions()) {
            orderModelResponse.customer.add(resourceLinkHelper.linkToUser(order.customer.id))
        }

        return orderModelResponse
    }

}