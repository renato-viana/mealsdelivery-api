package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.helper.ResourceLinkHelper
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.UserModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler.UserModelResponseAssembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller.RestaurantResponsibleUserControllerOpenApi
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
@RequestMapping(path = ["/v1/restaurants/{restaurantId}/responsible"])
class RestaurantResponsibleUserController(

    @Autowired
    private val restaurantService: RestaurantService,

    @Autowired
    private val userModelResponseAssembler: UserModelResponseAssembler,

    @Autowired
    private val resourceLinkHelper: ResourceLinkHelper,

    @Autowired
    private val security: Security

) : RestaurantResponsibleUserControllerOpenApi {

    @CheckSecurity.Restaurants.CanConsult
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun list(@PathVariable restaurantId: Long?): CollectionModel<UserModelResponse> {
        val restaurant: Restaurant = restaurantService.fetchOrFail(restaurantId)

        val userModelResponse: CollectionModel<UserModelResponse> =
            userModelResponseAssembler.toCollectionModel(restaurant.responsible)
                .removeLinks()

        userModelResponse.add(resourceLinkHelper.linkToResponsibleRestaurant(restaurantId))

        if (security.canManageRestaurantsRegistration()) {
            userModelResponse.add(
                resourceLinkHelper.linkToRestaurantResponsibleAssociation(restaurantId, "associate")
            )
            userModelResponse.content
                .forEach(Consumer { userModelResponse: UserModelResponse ->
                    userModelResponse.add(
                        resourceLinkHelper.linkToRestaurantResponsibleDisassociation(
                            restaurantId, userModelResponse.id, "disassociate"
                        )
                    )
                })
        }

        return userModelResponse
    }

    @CheckSecurity.Restaurants.CanManageRegistration
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun disassociate(@PathVariable restaurantId: Long?, @PathVariable userId: Long?): ResponseEntity<Void> {
        restaurantService.disassociateResponsible(restaurantId, userId)
        return ResponseEntity.noContent().build()
    }

    @CheckSecurity.Restaurants.CanManageRegistration
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun associate(@PathVariable restaurantId: Long?, @PathVariable userId: Long?): ResponseEntity<Void> {
        restaurantService.associateResponsible(restaurantId, userId)

        return ResponseEntity.noContent().build()
    }

}