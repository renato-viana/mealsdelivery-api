package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.model

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.UserModelResponse
import io.swagger.annotations.ApiModel
import org.springframework.hateoas.Links

@ApiModel("UsersModelResponseOpenApi")
class UsersModelResponseOpenApi(

    private val _embedded: UsersEmbeddedModelResponseOpenApi? = null,

    private val _links: Links? = null

) {

    @ApiModel("UsersEmbeddedModelResponse")
    companion object UsersEmbeddedModelResponseOpenApi {

        private val users: List<UserModelResponse>? = null

    }

}