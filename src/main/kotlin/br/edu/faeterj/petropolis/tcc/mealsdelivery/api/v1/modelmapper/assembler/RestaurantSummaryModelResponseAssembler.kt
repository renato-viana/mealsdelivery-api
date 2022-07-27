package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller.RestaurantController
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.helper.ResourceLinkHelper
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.RestaurantSummaryModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.Security
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Restaurant
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.stereotype.Component

@Component
class RestaurantSummaryModelResponseAssembler(

    @Autowired
    private val modelMapper: ModelMapper,

    @Autowired
    private val resourceLinkHelper: ResourceLinkHelper,

    @Autowired
    private val security: Security

) :
    RepresentationModelAssemblerSupport<Restaurant, RestaurantSummaryModelResponse>(
        RestaurantController::class.java, RestaurantSummaryModelResponse::class.java
    ) {

    override fun toModel(restaurant: Restaurant): RestaurantSummaryModelResponse {
        val restaurantModelResponse: RestaurantSummaryModelResponse =
            createModelWithId(restaurant.id ?: -1L, restaurant)

        modelMapper.map(restaurant, restaurantModelResponse)

        if (security.canConsultRestaurants()) {
            restaurantModelResponse.add(resourceLinkHelper.linkToRestaurants("restaurants"))
        }

        if (security.canConsultCuisines()) {
            restaurantModelResponse.cuisine.add(resourceLinkHelper.linkToCuisine(restaurant.cuisine.id!!))
        }

        return restaurantModelResponse
    }

    override fun toCollectionModel(entities: Iterable<Restaurant?>): CollectionModel<RestaurantSummaryModelResponse> {
        val collectionModelResponse: CollectionModel<RestaurantSummaryModelResponse> = super.toCollectionModel(entities)

        if (security.canConsultRestaurants()) {
            collectionModelResponse.add(resourceLinkHelper.linkToRestaurants())
        }

        return collectionModelResponse
    }

}