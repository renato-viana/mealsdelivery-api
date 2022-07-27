package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.exceptionhandler.Problem
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.StateModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.StateModelResponse
import io.swagger.annotations.*
import org.springframework.hateoas.CollectionModel

@Api(tags = ["States"])
interface StateControllerOpenApi {

    @ApiOperation("List of states")
    fun list(): CollectionModel<StateModelResponse>

    @ApiOperation("Fetch a state by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 400, message = "Invalid state ID", response = Problem::class),
            ApiResponse(code = 404, message = "State not found", response = Problem::class)
        ]
    )
    fun fetch(@ApiParam(value = "State ID", example = "1", required = true) stateId: Long?): StateModelResponse

    @ApiOperation("Register a state")
    @ApiResponses(ApiResponse(code = 201, message = "Registered state"))
    fun add(
        @ApiParam(
            name = "Body",
            value = "Representation of a registered state",
            required = true
        ) stateModelRequest: StateModelRequest
    ): StateModelResponse

    @ApiOperation("Update a state by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 200, message = "Updated state"),
            ApiResponse(code = 404, message = "State not found", response = Problem::class)
        ]
    )
    fun update(
        @ApiParam(value = "State ID", example = "1", required = true) stateId: Long,
        @ApiParam(
            name = "Body",
            value = "Representation of a state with updated data",
            required = true
        ) stateModelRequest: StateModelRequest
    ): StateModelResponse

    @ApiOperation("Delete a state by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 204, message = "State deleted"),
            ApiResponse(code = 404, message = "State not found", response = Problem::class)
        ]
    )
    fun remove(@ApiParam(value = "State ID", example = "1", required = true) stateId: Long)

}