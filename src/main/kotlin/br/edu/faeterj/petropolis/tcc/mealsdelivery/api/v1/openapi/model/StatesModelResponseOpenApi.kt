package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.model

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.StateModelResponse
import io.swagger.annotations.ApiModel
import org.springframework.hateoas.Links

@ApiModel("StatesModelResponseOpenApi")
class StatesModelResponseOpenApi(

    private val _embedded: StatesEmbeddedModelResponseOpenApi? = null,

    private val _links: Links? = null

) {


    @ApiModel("StatesEmbeddedModelResponse")
    companion object StatesEmbeddedModelResponseOpenApi {

        private val states: List<StateModelResponse>? = null

    }

}