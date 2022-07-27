package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.helper.ResourceLinkHelper
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.PaymentMethodModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler.PaymentMethodModelResponseAssembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller.RestaurantPaymentMethodControllerOpenApi
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.CheckSecurity
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.Security
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Restaurant
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.RestaurantService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.function.Consumer

@RestController
@RequestMapping(path = ["/v1/restaurants/{restaurantId}/payment-methods"])
class RestaurantPaymentMethodController(

    @Autowired
    private val restaurantService: RestaurantService,

    @Autowired
    private val paymentMethodModelResponseAssembler: PaymentMethodModelResponseAssembler,

    @Autowired
    private val resourceLinkHelper: ResourceLinkHelper,

    @Autowired
    private val security: Security

) : RestaurantPaymentMethodControllerOpenApi {

    @CheckSecurity.Restaurants.CanConsult
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun list(@PathVariable restaurantId: Long?): CollectionModel<PaymentMethodModelResponse> {
        val restaurant: Restaurant = restaurantService.fetchOrFail(restaurantId)

        val paymentMethodsModelResponse: CollectionModel<PaymentMethodModelResponse> =
            paymentMethodModelResponseAssembler.toCollectionModel(restaurant.paymentMethods)
                .removeLinks()

        paymentMethodsModelResponse.add(resourceLinkHelper.linkToRestaurantPaymentMethods(restaurantId))

        if (security.canManageTheOperationOfRestaurants(restaurantId)) {
            paymentMethodsModelResponse.add(
                resourceLinkHelper.linkToRestaurantPaymentMethodAssociation(restaurantId, "associate")
            )
            paymentMethodsModelResponse.content.forEach(
                Consumer { paymentMethodModelResponse: PaymentMethodModelResponse ->
                    paymentMethodModelResponse.add(
                        resourceLinkHelper
                            .linkToRestaurantPaymentMethodDisassociation(
                                restaurantId, paymentMethodModelResponse.id, "disassociate"
                            )
                    )
                }
            )
        }

        return paymentMethodsModelResponse
    }

    @CheckSecurity.Restaurants.CanManageOperation
    @DeleteMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun disassociate(@PathVariable restaurantId: Long?, @PathVariable paymentMethodId: Long?):
            ResponseEntity<Void> {
        restaurantService.disassociatePaymentMethod(restaurantId, paymentMethodId)

        return ResponseEntity.noContent().build()
    }

    @CheckSecurity.Restaurants.CanManageOperation
    @PutMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun associate(@PathVariable restaurantId: Long?, @PathVariable paymentMethodId: Long?):
            ResponseEntity<Void> {

        restaurantService.associatePaymentMethod(restaurantId, paymentMethodId)

        return ResponseEntity.noContent().build()
    }

}