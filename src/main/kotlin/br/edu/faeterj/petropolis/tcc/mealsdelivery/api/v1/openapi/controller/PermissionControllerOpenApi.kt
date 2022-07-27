package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.PermissionModelResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.hateoas.CollectionModel

@Api(tags = ["Permissions"])
interface PermissionControllerOpenApi {

    @ApiOperation("List of permissions")
    fun list(): CollectionModel<PermissionModelResponse>

}