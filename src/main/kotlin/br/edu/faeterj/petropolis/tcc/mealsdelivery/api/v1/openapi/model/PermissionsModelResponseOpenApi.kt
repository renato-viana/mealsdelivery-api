package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.model

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.PermissionModelResponse
import io.swagger.annotations.ApiModel
import org.springframework.hateoas.Links

@ApiModel("PermissionsModelResponseOpenApi")
class PermissionsModelResponseOpenApi(

    private val _embedded: PermissionsEmbeddedModelResponseOpenApi? = null,

    private val _links: Links? = null

) {


    @ApiModel("PermissionsEmbeddedModelResponse")
    companion object PermissionsEmbeddedModelResponseOpenApi {
        private val permissions: List<PermissionModelResponse>? = null
    }

}