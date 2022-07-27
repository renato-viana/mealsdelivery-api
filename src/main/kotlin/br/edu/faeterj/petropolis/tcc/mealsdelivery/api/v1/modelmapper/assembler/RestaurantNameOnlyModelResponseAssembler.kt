package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller.RestaurantController
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.helper.ResourceLinkHelper
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.RestaurantNameOnlyModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.Security
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Restaurant
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.stereotype.Component

@Component
class RestaurantNameOnlyModelResponseAssembler(

    @Autowired
    private val modelMapper: ModelMapper,

    @Autowired
    private val resourceLinkHelper: ResourceLinkHelper,

    @Autowired
    private val security: Security

) : RepresentationModelAssemblerSupport<Restaurant, RestaurantNameOnlyModelResponse>(
    RestaurantController::class.java, RestaurantNameOnlyModelResponse::class.java
) {

    override fun toModel(restaurant: Restaurant): RestaurantNameOnlyModelResponse {
        val restaurantModelResponse: RestaurantNameOnlyModelResponse =
            createModelWithId(restaurant.id ?: -1L, restaurant)

        modelMapper.map(restaurant, restaurantModelResponse)

        if (security.canConsultRestaurants()) {
            restaurantModelResponse.add(resourceLinkHelper.linkToRestaurants("restaurants"))
        }

        return restaurantModelResponse
    }

    override fun toCollectionModel(entities: Iterable<Restaurant?>): CollectionModel<RestaurantNameOnlyModelResponse> {
        val collectionModelResponse: CollectionModel<RestaurantNameOnlyModelResponse> =
            super.toCollectionModel(entities)

        if (security.canConsultRestaurants()) {
            collectionModelResponse.add(resourceLinkHelper.linkToRestaurants())
        }

        return collectionModelResponse
    }

}