package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.model

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.CuisineModelResponse
import io.swagger.annotations.ApiModel
import org.springframework.hateoas.Links

@ApiModel("CuisinesModelResponseOpenApi")
class CuisinesModelResponseOpenApi(

    private val _embedded: CuisinesEmbeddedModelResponseOpenApi? = null,

    private val _links: Links? = null,

    private val page: PagedModelResponseOpenApi? = null

) {

    @ApiModel("CuisinesEmbeddedModelResponse")
    companion object CuisinesEmbeddedModelResponseOpenApi {

        private val cuisines: List<CuisineModelResponse>? = null

    }

}