package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.helper

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller.*
import org.springframework.hateoas.*
import org.springframework.hateoas.TemplateVariable.VariableType
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.stereotype.Component


@Component
class ResourceLinkHelper {
    fun linkToOrders(rel: String): Link {
        val filterVariables = TemplateVariables(
            TemplateVariable("clientId", VariableType.REQUEST_PARAM),
            TemplateVariable("restaurantId", VariableType.REQUEST_PARAM),
            TemplateVariable("creationDateStart", VariableType.REQUEST_PARAM),
            TemplateVariable("creationDateEnd", VariableType.REQUEST_PARAM)
        )
        val ordersUrl: String = linkTo(OrderController::class.java).toUri().toString()

        return Link.of(
            UriTemplate.of(
                ordersUrl,
                PAGINATION_VARIABLES.concat(filterVariables)
            ), rel
        )
    }

    fun linkToOrderConfirmation(orderCode: String, rel: String): Link {
        return linkTo(methodOn(OrderStatusController::class.java).confirm(orderCode)).withRel(rel)
    }

    fun linkToOrderDelivery(orderCode: String, rel: String): Link {
        return linkTo(methodOn(OrderStatusController::class.java).deliver(orderCode)).withRel(rel)
    }

    fun linkToOrderCancellation(orderCode: String, rel: String): Link {
        return linkTo(methodOn(OrderStatusController::class.java).cancel(orderCode)).withRel(rel)
    }

    @JvmOverloads
    fun linkToRestaurant(restaurantId: Long?, rel: String = IanaLinkRelations.SELF.value()): Link {
        return linkTo(methodOn(RestaurantController::class.java).fetch(restaurantId)).withRel(rel)
    }

    @JvmOverloads
    fun linkToUser(userId: Long?, rel: String = IanaLinkRelations.SELF.value()): Link {
        return linkTo(methodOn(UserController::class.java).fetch(userId)).withRel(rel)
    }

    @JvmOverloads
    fun linkToUsers(rel: String = IanaLinkRelations.SELF.value()): Link {
        return linkTo(UserController::class.java).withRel(rel)
    }

    @JvmOverloads
    fun linkToUserRoles(userId: Long?, rel: String = IanaLinkRelations.SELF.value()): Link {
        return linkTo(methodOn(UserRoleController::class.java).list(userId)).withRel(rel)
    }

    @JvmOverloads
    fun linkToResponsibleRestaurant(restaurantId: Long?, rel: String = IanaLinkRelations.SELF.value()): Link {
        return linkTo(methodOn(RestaurantResponsibleUserController::class.java).list(restaurantId)).withRel(rel)
    }

    @JvmOverloads
    fun linkToPaymentMethod(paymentMethodId: Long?, rel: String = IanaLinkRelations.SELF.value()): Link {
        return linkTo(methodOn(PaymentMethodController::class.java).fetch(paymentMethodId, null)!!).withRel(rel)
    }

    @JvmOverloads
    fun linkToCity(cityId: Long?, rel: String = IanaLinkRelations.SELF.value()): Link {
        return linkTo(methodOn(CityController::class.java).fetch(cityId)).withRel(rel)
    }

    @JvmOverloads
    fun linkToCities(rel: String = IanaLinkRelations.SELF.value()): Link {
        return linkTo(CityController::class.java).withRel(rel)
    }

    @JvmOverloads
    fun linkToState(stateId: Long?, rel: String = IanaLinkRelations.SELF.value()): Link {
        return linkTo(methodOn(StateController::class.java).fetch(stateId)).withRel(rel)
    }

    @JvmOverloads
    fun linkToStates(rel: String = IanaLinkRelations.SELF.value()): Link {
        return linkTo(StateController::class.java).withRel(rel)
    }

    @JvmOverloads
    fun linkToProduct(restaurantId: Long?, productId: Long?, rel: String = IanaLinkRelations.SELF.value()): Link {
        return linkTo(methodOn(ProductController::class.java).fetch(restaurantId, productId)).withRel(rel)
    }

    @JvmOverloads
    fun linkToCuisines(rel: String = IanaLinkRelations.SELF.value()): Link {
        return linkTo(CuisineController::class.java).withRel(rel)
    }

    @JvmOverloads
    fun linkToCuisine(cuisineId: Long, rel: String = IanaLinkRelations.SELF.value()): Link {
        return linkTo(methodOn(CuisineController::class.java).fetch(cuisineId)).withRel(rel)
    }

    @JvmOverloads
    fun linkToRestaurants(rel: String = IanaLinkRelations.SELF.value()): Link {
        val restaurantsUrl: String = linkTo(RestaurantController::class.java).toUri().toString()

        return Link.of(UriTemplate.of(restaurantsUrl, PROJECTION_VARIABLES), rel)
    }

    @JvmOverloads
    fun linkToRestaurantPaymentMethods(restaurantId: Long?, rel: String = IanaLinkRelations.SELF.value()): Link {
        return linkTo(methodOn(RestaurantPaymentMethodController::class.java).list(restaurantId)).withRel(rel)
    }

    fun linkToRestaurantPaymentMethodDisassociation(
        restaurantId: Long?,
        paymentMethodId: Long?,
        rel: String
    ): Link {
        return linkTo(
            methodOn(RestaurantPaymentMethodController::class.java).disassociate(restaurantId, paymentMethodId)
        )
            .withRel(rel)
    }

    fun linkToRestaurantPaymentMethodAssociation(restaurantId: Long?, rel: String): Link {
        return linkTo(
            methodOn(RestaurantPaymentMethodController::class.java).associate(restaurantId, null)
        )
            .withRel(rel)
    }

    fun linkToRestaurantResponsible(restaurantId: Long?, rel: String): Link {
        return linkTo(
            methodOn(RestaurantResponsibleUserController::class.java).list(restaurantId)
        )
            .withRel(rel)
    }

    fun linkToRestaurantResponsibleDisassociation(
        restaurantId: Long?, userId: Long?, rel: String
    ): Link {
        return linkTo(
            methodOn(RestaurantResponsibleUserController::class.java).disassociate(restaurantId, userId)
        )
            .withRel(rel)
    }

    fun linkToRestaurantResponsibleAssociation(restaurantId: Long?, rel: String): Link {
        return linkTo(
            methodOn(RestaurantResponsibleUserController::class.java).associate(restaurantId, null)
        ).withRel(rel)
    }


    fun linkToRestaurantOpening(restaurantId: Long?, rel: String): Link {
        return linkTo(
            methodOn(RestaurantController::class.java).open(restaurantId)
        ).withRel(rel)
    }

    fun linkToRestaurantClosure(restaurantId: Long?, rel: String): Link {
        return linkTo(
            methodOn(RestaurantController::class.java).close(restaurantId)
        ).withRel(rel)
    }

    fun linkToRestaurantInactivation(restaurantId: Long?, rel: String): Link {
        return linkTo(methodOn(RestaurantController::class.java).inactivate(restaurantId)).withRel(rel)
    }

    fun linkToRestaurantActivation(restaurantId: Long?, rel: String): Link {
        return linkTo(methodOn(RestaurantController::class.java).activate(restaurantId)).withRel(rel)
    }

    @JvmOverloads
    fun linkToPaymentMethods(rel: String = IanaLinkRelations.SELF.value()): Link {
        return linkTo(PaymentMethodController::class.java).withRel(rel)
    }

    @JvmOverloads
    fun linkToProducts(restaurantId: Long?, rel: String = IanaLinkRelations.SELF.value()): Link {
        return linkTo(methodOn(ProductController::class.java).list(restaurantId, null)).withRel(rel)
    }

    @JvmOverloads
    fun linkToProductImage(restaurantId: Long?, productId: Long?, rel: String = IanaLinkRelations.SELF.value()): Link {
        return linkTo(methodOn(ProductImageController::class.java).fetch(restaurantId, productId)).withRel(rel)
    }

    @JvmOverloads
    fun linkToRoles(rel: String = IanaLinkRelations.SELF.value()): Link {
        return linkTo(RoleController::class.java).withRel(rel)
    }

    @JvmOverloads
    fun linkToRolePermissions(roleId: Long?, rel: String = IanaLinkRelations.SELF.value()): Link {
        return linkTo(methodOn(RolePermissionController::class.java).list(roleId)).withRel(rel)
    }

    @JvmOverloads
    fun linkToPermissions(rel: String = IanaLinkRelations.SELF.value()): Link {
        return linkTo(PermissionController::class.java).withRel(rel)
    }

    fun linkToRolePermissionAssociation(roleId: Long?, rel: String): Link {
        return linkTo(methodOn(RolePermissionController::class.java).associate(roleId, null)).withRel(rel)
    }

    fun linkToRolePermissionDisassociation(roleId: Long?, permissionId: Long?, rel: String): Link {
        return linkTo(methodOn(RolePermissionController::class.java).disassociate(roleId, permissionId)).withRel(rel)
    }

    fun linkToUserRoleAssociation(userId: Long?, rel: String): Link {
        return linkTo(methodOn(UserRoleController::class.java).associate(userId, null)).withRel(rel)
    }

    fun linkToUserRoleDisassociation(userId: Long?, roleId: Long?, rel: String): Link {
        return linkTo(methodOn(UserRoleController::class.java).disassociate(userId, roleId)).withRel(rel)
    }

    fun linkToStatistics(rel: String): Link {
        return linkTo(StatisticsController::class.java).withRel(rel)
    }

    fun linkToStatisticsDailySales(rel: String): Link {
        val filterVariables = TemplateVariables(
            TemplateVariable("restaurantId", VariableType.REQUEST_PARAM),
            TemplateVariable("creationDateStart", VariableType.REQUEST_PARAM),
            TemplateVariable("creationDateEnd", VariableType.REQUEST_PARAM),
            TemplateVariable("timeOffset", VariableType.REQUEST_PARAM)
        )
        val ordersUrl: String = linkTo(
            methodOn(StatisticsController::class.java).consultDailySales(null, null)!!
        )
            .toUri().toString()

        return Link.of(UriTemplate.of(ordersUrl, filterVariables), rel)
    }

    companion object {

        val PAGINATION_VARIABLES: TemplateVariables = TemplateVariables(
            TemplateVariable("page", VariableType.REQUEST_PARAM),
            TemplateVariable("size", VariableType.REQUEST_PARAM),
            TemplateVariable("sort", VariableType.REQUEST_PARAM)
        )
        val PROJECTION_VARIABLES: TemplateVariables = TemplateVariables(
            TemplateVariable("projection", VariableType.REQUEST_PARAM)
        )

    }

}