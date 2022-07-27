package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.exceptionhandler.Problem
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.CuisineModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.CuisineModelResponse
import io.swagger.annotations.*
import org.springframework.data.domain.Pageable
import org.springframework.hateoas.PagedModel

@Api(tags = ["Cuisines"])
interface CuisineControllerOpenApi {

    @ApiOperation("List of cuisines with pagination")
    fun list(pageable: Pageable): PagedModel<CuisineModelResponse>

    @ApiOperation("Fetch a cuisine by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 400, message = "Invalid cuisine ID", response = Problem::class),
            ApiResponse(code = 404, message = "Cuisine not found", response = Problem::class)
        ]
    )
    fun fetch(
        @ApiParam(value = "Cuisine ID", example = "1", required = true) cuisineId: Long
    ): CuisineModelResponse

    @ApiOperation("Register a cuisine")
    @ApiResponses(
        *[ApiResponse(code = 201, message = "Registered cuisine")]
    )
    fun add(
        @ApiParam(
            name = "Body",
            value = "Representation of a registered cuisine",
            required = true
        ) cuisineModelRequest: CuisineModelRequest
    ): CuisineModelResponse

    @ApiOperation("Update a cuisine by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 200, message = "Updated cuisine"),
            ApiResponse(code = 404, message = "Cuisine not found", response = Problem::class)
        ]
    )
    fun update(
        @ApiParam(value = "Cuisine ID", example = "1", required = true) cuisineId: Long,
        @ApiParam(
            name = "Body",
            value = "Representation of a cuisine with updated data",
            required = true
        ) cuisineModelRequest: CuisineModelRequest
    ): CuisineModelResponse

    @ApiOperation("Delete a cuisine by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 204, message = "Cuisine deleted"),
            ApiResponse(code = 404, message = "Cuisine not found", response = Problem::class)
        ]
    )
    fun remove(
        @ApiParam(value = "Cuisine ID", example = "1", required = true) cuisineId: Long
    )

}