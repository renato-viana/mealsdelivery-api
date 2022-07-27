package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.model

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.RestaurantSummaryModelResponse
import io.swagger.annotations.ApiModel
import org.springframework.hateoas.Links

@ApiModel("RestaurantsSummaryModelResponseOpenApi")
class RestaurantsSummaryModelResponseOpenApi(

    private val _embedded: RestaurantSummaryEmbeddedModelResponseOpenApi? = null,

    private val _links: Links? = null

) {


    @ApiModel("RestaurantSummaryEmbeddedModelResponse")
    companion object RestaurantSummaryEmbeddedModelResponseOpenApi {

        private val restaurants: List<RestaurantSummaryModelResponse>? = null

    }

}