package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller.RootEntryPointController.RootEntryPointModelResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation

@Api(tags = ["Root Entry Point"])
interface RootEntryPointControllerOpenApi {

    @ApiOperation("Lists the resources the user has access")
    fun root(): RootEntryPointModelResponse

}