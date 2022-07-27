package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security

import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PreAuthorize

annotation class CheckSecurity {
    annotation class Cuisines {
        @PreAuthorize("@security.canConsultCuisines()")
        @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
        @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
        annotation class CanConsult

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_CUISINE')")
        @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
        @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
        annotation class CanEdit
    }

    annotation class Restaurants {
        @PreAuthorize("@security.canManageRestaurantsRegistration()")
        @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
        @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
        annotation class CanManageRegistration

        @PreAuthorize("@security.canManageTheOperationOfRestaurants(#restaurantId)")
        @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
        @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
        annotation class CanManageOperation

        @PreAuthorize("@security.canConsultRestaurants()")
        @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
        @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
        annotation class CanConsult
    }

    annotation class Orders {
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize(
            "hasAuthority('CONSULT_ORDERS') or " +
                    "@security.isUserAuthenticatedEqual(returnObject.customer.id) or " +
                    "@security.managesRestaurant(returnObject.restaurant.id)"
        )
        @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
        @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
        annotation class CanFetch

        @PreAuthorize("@security.canSearchOrders(#filter.customerId, #filter.restaurantId)")
        @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
        @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
        annotation class CanSearch

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
        @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
        annotation class CanCreate

        @PreAuthorize("@security.canManageOrders(#orderCode)")
        @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
        @Target(
            AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER
        )
        annotation class CanManageOrders
    }

    annotation class PaymentMethods {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_PAYMENT_METHODS')")
        @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
        @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
        annotation class CanEdit

        @PreAuthorize("@security.canConsultPaymentMethods()")
        @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
        @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
        annotation class CanConsult
    }

    annotation class Cities {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_CITIES')")
        @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
        @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
        annotation class CanEdit

        @PreAuthorize("@security.canConsultCities()")
        @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
        @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
        annotation class CanConsult
    }

    annotation class States {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_STATES')")
        @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
        @Target(
            AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER
        )
        annotation class CanEdit

        @PreAuthorize("@security.canConsultStates()")
        @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
        @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
        annotation class CanConsult
    }

    annotation class UsersRolesPermissions {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and @security.isUserAuthenticatedEqual(#userId)")
        @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
        @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
        annotation class CanChangeOwnPassword

        @PreAuthorize(
            "hasAuthority('SCOPE_WRITE') and (hasAuthority('EDIT_USERS_ROLES_PERMISSIONS') or "
                    + "@security.isUserAuthenticatedEqual(#userId))"
        )
        @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
        @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
        annotation class CanChangeUser

        @PreAuthorize("@security.canEditUsersRolesPermissions()")
        @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
        @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
        annotation class CanEdit

        @PreAuthorize("@security.canConsultUsersRolesPermissions()")
        @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
        @Target(
            AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER
        )
        annotation class CanConsult
    }

    annotation class Statistics {
        @PreAuthorize("@security.canConsultStatistics()")
        @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
        @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
        annotation class CanConsult
    }
}