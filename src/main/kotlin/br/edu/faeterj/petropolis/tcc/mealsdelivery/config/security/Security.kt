package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.OrderRepository
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.RestaurantRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component

@Component
class Security(

    @Autowired
    private val restaurantRepository: RestaurantRepository,

    @Autowired
    private val orderRepository: OrderRepository

) {

    fun getAuthentication(): Authentication = SecurityContextHolder.getContext().authentication

    fun getUserId(): Long {
        val jwt: Jwt = getAuthentication().principal as Jwt

        return jwt.getClaim("user_id")
    }

    fun managesRestaurant(restaurantId: Long?): Boolean =
        if (restaurantId == null) false else restaurantRepository.isResponsible(restaurantId, getUserId())

    fun managesRestaurantOrder(orderCode: String): Boolean = orderRepository.isOrderManagedBy(orderCode, getUserId())

    fun isUserAuthenticatedEqual(userId: Long?): Boolean = getUserId() == userId

    fun hasAuthority(authorityName: String): Boolean = getAuthentication().authorities.stream()
        .anyMatch { it.authority.equals(authorityName) }

    fun canManageOrders(orderCode: String): Boolean = hasAuthority("SCOPE_WRITE") &&
            (hasAuthority("MANAGE_ORDERS") || managesRestaurantOrder(orderCode))

    fun isAuthenticated(): Boolean = getAuthentication().isAuthenticated

    fun hasWriteScope(): Boolean = hasAuthority("SCOPE_WRITE")

    fun hasReadScope(): Boolean = hasAuthority("SCOPE_READ")

    fun canConsultRestaurants(): Boolean = hasReadScope() && isAuthenticated()

    fun canManageRestaurantsRegistration(): Boolean = hasWriteScope() && hasAuthority("EDIT_RESTAURANTS")

    fun canManageTheOperationOfRestaurants(restaurantId: Long?): Boolean =
        hasWriteScope() && (hasAuthority("EDIT_RESTAURANTS") || managesRestaurant(restaurantId))

    fun canConsultUsersRolesPermissions(): Boolean =
        hasReadScope() && hasAuthority("CONSULT_USERS_ROLES_PERMISSIONS")

    fun canEditUsersRolesPermissions(): Boolean =
        hasWriteScope() && hasAuthority("EDIT_USERS_ROLES_PERMISSIONS")

    fun canSearchOrders(customerIdId: Long?, restaurantId: Long?): Boolean =
        hasReadScope() && (hasAuthority("CONSULT_ORDERS") ||
                isUserAuthenticatedEqual(customerIdId) || managesRestaurant(restaurantId))

    fun canSearchOrders(): Boolean = isAuthenticated() && hasReadScope()

    fun canConsultPaymentMethods(): Boolean = isAuthenticated() && hasReadScope()

    fun canConsultCities(): Boolean = isAuthenticated() && hasReadScope()

    fun canConsultStates(): Boolean = isAuthenticated() && hasReadScope()

    fun canConsultCuisines(): Boolean = isAuthenticated() && hasReadScope()

    fun canConsultStatistics(): Boolean = hasReadScope() && hasAuthority("GENERATE_REPORTS")

}