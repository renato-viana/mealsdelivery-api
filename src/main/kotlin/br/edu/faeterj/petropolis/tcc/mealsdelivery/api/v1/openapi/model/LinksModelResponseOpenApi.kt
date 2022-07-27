package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.model

import io.swagger.annotations.ApiModel

@ApiModel("LinksModelResponseOpenApi")
class LinksModelResponseOpenApi(

    private val rel: LinkModelResponse? = null

) {


    @ApiModel("Link")
    companion object LinkModelResponse {

        private val href: String? = null

        private val templated = false

    }

}