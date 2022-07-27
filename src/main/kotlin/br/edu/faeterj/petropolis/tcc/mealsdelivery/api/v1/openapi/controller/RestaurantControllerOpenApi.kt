package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.exceptionhandler.Problem
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.RestaurantModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.RestaurantModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.RestaurantNameOnlyModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.RestaurantSummaryModelResponse
import io.swagger.annotations.*
import org.springframework.hateoas.CollectionModel
import org.springframework.http.ResponseEntity
import springfox.documentation.annotations.ApiIgnore

@Api(tags = ["Restaurants"])
interface RestaurantControllerOpenApi {

    @ApiOperation(value = "List of restaurants")
    @ApiImplicitParams(
        *[
            ApiImplicitParam(
                value = "Query parameter name",
                allowableValues = "just-name",
                name = "projection",
                paramType = "query",
                type = "string"
            )]
    )
    fun list(): CollectionModel<RestaurantSummaryModelResponse>

    @ApiIgnore
    @ApiOperation(value = "List of restaurants", hidden = true)
    fun listNamesOnly(): CollectionModel<RestaurantNameOnlyModelResponse>

    @ApiOperation("Fetch a restaurant by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 400, message = "Invalid restaurant ID", response = Problem::class),
            ApiResponse(code = 404, message = "Restaurant not found", response = Problem::class)
        ]
    )
    fun fetch(
        @ApiParam(value = "Restaurant ID", example = "1", required = true) restaurantId: Long?
    ): RestaurantModelResponse

    @ApiOperation("Register a restaurant")
    @ApiResponses(*[ApiResponse(code = 201, message = "Registered restaurant")])
    fun add(
        @ApiParam(
            name = "Body",
            value = "Representation of a registered restaurant",
            required = true
        ) restaurantModelRequest: RestaurantModelRequest
    ): RestaurantModelResponse

    @ApiOperation("Update a restaurant by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 200, message = "Updated restaurant"),
            ApiResponse(code = 404, message = "Restaurant not found", response = Problem::class)
        ]
    )
    fun update(
        @ApiParam(value = "Restaurant ID", example = "1", required = true) restaurantId: Long,
        @ApiParam(
            name = "Body",
            value = "Representation of a restaurant with updated data",
            required = true
        ) restaurantModelRequest: RestaurantModelRequest
    ): RestaurantModelResponse

    @ApiOperation("Activate a restaurant by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 204, message = "Restaurant successfully activated"),
            ApiResponse(code = 404, message = "Restaurant not found", response = Problem::class)
        ]
    )
    fun activate(
        @ApiParam(value = "Restaurant ID", example = "1", required = true) restaurantId: Long?
    ): ResponseEntity<Void>

    @ApiOperation("Inactivate a restaurant by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 204, message = "Restaurant successfully inactivated"),
            ApiResponse(code = 404, message = "Restaurant not found", response = Problem::class)
        ]
    )
    fun inactivate(
        @ApiParam(value = "Restaurant ID", example = "1", required = true) restaurantId: Long?
    ): ResponseEntity<Void>

    @ApiOperation("Activate many restaurants")
    @ApiResponses(*[ApiResponse(code = 204, message = "Restaurants successfully activated")])
    fun activateMany(
        @ApiParam(name = "Body", value = "Restaurants IDs", required = true) restaurantsId: List<Long>
    )

    @ApiOperation("Inactivate many restaurants")
    @ApiResponses(*[ApiResponse(code = 204, message = "Restaurants successfully inactivated")])
    fun inactivateMany(
        @ApiParam(name = "Body", value = "Restaurants IDs", required = true) restaurantsId: List<Long>
    )

    @ApiOperation("Open a restaurant by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 204, message = "Restaurant successfully opened"),
            ApiResponse(code = 404, message = "Restaurant not found", response = Problem::class)
        ]
    )
    fun open(
        @ApiParam(value = "Restaurant ID", example = "1", required = true) restaurantId: Long?
    ): ResponseEntity<Void>

    @ApiOperation("Close a restaurant by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 204, message = "Restaurant successfully closed"),
            ApiResponse(code = 404, message = "Restaurant not found", response = Problem::class)
        ]
    )
    fun close(
        @ApiParam(value = "Restaurant ID", example = "1", required = true) restaurantId: Long?
    ): ResponseEntity<Void>

}