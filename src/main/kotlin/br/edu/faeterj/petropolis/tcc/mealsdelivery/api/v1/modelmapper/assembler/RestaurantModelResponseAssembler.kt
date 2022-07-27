package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller.RestaurantController
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.helper.ResourceLinkHelper
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.RestaurantModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.Security
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Restaurant
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.stereotype.Component

@Component
class RestaurantModelResponseAssembler(

    @Autowired
    private val modelMapper: ModelMapper,

    @Autowired
    private val resourceLinkHelper: ResourceLinkHelper,

    @Autowired
    private val security: Security

) : RepresentationModelAssemblerSupport<Restaurant, RestaurantModelResponse>(
    RestaurantController::class.java, RestaurantModelResponse::class.java
) {

    override fun toModel(restaurant: Restaurant): RestaurantModelResponse {
        val restaurantModelResponse: RestaurantModelResponse = createModelWithId(restaurant.id ?: -1L, restaurant)

        modelMapper.map(restaurant, restaurantModelResponse)

        if (security.canConsultRestaurants()) {
            restaurantModelResponse.add(resourceLinkHelper.linkToRestaurants("restaurants"))
        }

        if (security.canManageRestaurantsRegistration()) {
            if (restaurant.allowedActivation()) {
                restaurantModelResponse.add(
                    resourceLinkHelper.linkToRestaurantActivation(restaurant.id, "active")
                )
            }

            if (restaurant.allowedInactivation()) {
                restaurantModelResponse.add(
                    resourceLinkHelper.linkToRestaurantInactivation(restaurant.id, "inactive")
                )
            }
        }

        if (security.canManageTheOperationOfRestaurants(restaurant.id)) {
            if (restaurant.allowedOpening()) {
                restaurantModelResponse.add(resourceLinkHelper.linkToRestaurantOpening(restaurant.id, "open"))
            }

            if (restaurant.closingAllowed()) {
                restaurantModelResponse.add(resourceLinkHelper.linkToRestaurantClosure(restaurant.id, "close"))
            }
        }

        if (security.canConsultRestaurants()) {
            restaurantModelResponse.add(resourceLinkHelper.linkToProducts(restaurant.id, "products"))
        }

        if (security.canConsultCuisines()) {
            restaurantModelResponse.cuisine.add(resourceLinkHelper.linkToCuisine(restaurant.cuisine.id!!))
        }

        if (security.canConsultCities()) {
            if (restaurantModelResponse.address != null && restaurantModelResponse.address?.city != null) {
                restaurantModelResponse.address?.city?.add(resourceLinkHelper.linkToCity(restaurant.address.city?.id))
            }
        }

        if (security.canConsultRestaurants()) {
            restaurantModelResponse.add(
                resourceLinkHelper.linkToRestaurantPaymentMethods(restaurant.id, "paymentMethods")
            )
        }

        if (security.canManageRestaurantsRegistration()) {
            restaurantModelResponse.add(
                resourceLinkHelper.linkToRestaurantResponsible(restaurant.id, "responsible")
            )
        }

        return restaurantModelResponse
    }

    override fun toCollectionModel(entities: Iterable<Restaurant?>): CollectionModel<RestaurantModelResponse> {
        val collectionModelResponse: CollectionModel<RestaurantModelResponse> = super.toCollectionModel(entities)

        if (security.canConsultRestaurants()) {
            collectionModelResponse.add(resourceLinkHelper.linkToRestaurants())
        }

        return collectionModelResponse
    }

}