package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.model

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.RoleModelResponse
import io.swagger.annotations.ApiModel
import org.springframework.hateoas.Links

@ApiModel("RolesModelResponseOpenApi")
class RolesModelResponseOpenApi(

    private val _embedded: RolesEmbeddedModelResponseOpenApi? = null,

    private val _links: Links? = null

) {

    @ApiModel("RolesEmbeddedModelResponse")
    companion object RolesEmbeddedModelResponseOpenApi {
        private val role: List<RoleModelResponse>? = null
    }

}