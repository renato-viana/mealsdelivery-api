package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.model

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.CityModelResponse
import io.swagger.annotations.ApiModel
import org.springframework.hateoas.Links

@ApiModel("CitiesModelResponseOpenApi")
class CitiesModelResponseOpenApi(

    private val _embedded: CitiesEmbeddedModelResponseOpenApi? = null,

    private val _links: Links? = null

) {

    @ApiModel("CitiesEmbeddedModelResponse")
    companion object CitiesEmbeddedModelResponseOpenApi {

        private val Cities: List<CityModelResponse>? = null

    }

}