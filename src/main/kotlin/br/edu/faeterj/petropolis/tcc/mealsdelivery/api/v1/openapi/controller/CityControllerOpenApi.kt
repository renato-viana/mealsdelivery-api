package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.exceptionhandler.Problem
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.CityModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.CityModelResponse
import io.swagger.annotations.*
import org.springframework.hateoas.CollectionModel

@Api(tags = ["Cities"])
interface CityControllerOpenApi {

    @ApiOperation("List of cities")
    fun list(): CollectionModel<CityModelResponse>

    @ApiOperation("Fetch a city by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 400, message = "Invalid city ID", response = Problem::class),
            ApiResponse(code = 404, message = "City not found", response = Problem::class)
        ]
    )
    fun fetch(@ApiParam(value = "City ID", example = "1", required = true) cityId: Long?): CityModelResponse

    @ApiOperation("Register a city")
    @ApiResponses(*[ApiResponse(code = 201, message = "Registered city")])
    fun add(
        @ApiParam(
            name = "Body",
            value = "Representation of a registered city",
            required = true
        ) cityModelRequest: CityModelRequest
    ): CityModelResponse

    @ApiOperation("Update a city by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 200, message = "Updated city"),
            ApiResponse(code = 404, message = "City not found", response = Problem::class)
        ]
    )
    fun update(
        @ApiParam(value = "City ID", example = "1", required = true) cityId: Long,
        @ApiParam(
            name = "Body",
            value = "Representation of a city with updated data",
            required = true
        ) cityModelRequest: CityModelRequest
    ): CityModelResponse

    @ApiOperation("Delete a city by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 204, message = "City deleted"),
            ApiResponse(code = 404, message = "City not found", response = Problem::class)
        ]
    )
    fun remove(
        @ApiParam(value = "City ID", example = "1", required = true) cityId: Long
    )

}