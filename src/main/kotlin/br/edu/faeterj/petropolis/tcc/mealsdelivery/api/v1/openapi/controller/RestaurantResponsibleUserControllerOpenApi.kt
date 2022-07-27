package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.exceptionhandler.Problem
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.UserModelResponse
import io.swagger.annotations.*
import org.springframework.hateoas.CollectionModel
import org.springframework.http.ResponseEntity

@Api(tags = ["Restaurants"])
interface RestaurantResponsibleUserControllerOpenApi {

    @ApiOperation("List responsible users associated with restaurant")
    @ApiResponses(*[ApiResponse(code = 404, message = "Restaurant not found", response = Problem::class)])
    fun list(
        @ApiParam(value = "Restaurant ID", example = "1", required = true) restaurantId: Long?
    ): CollectionModel<UserModelResponse>

    @ApiOperation("Disassociation of restaurant with responsible user")
    @ApiResponses(
        *[
            ApiResponse(code = 204, message = "Successfully disassociated"),
            ApiResponse(code = 404, message = "Restaurant or user not found", response = Problem::class)
        ]
    )
    fun disassociate(
        @ApiParam(value = "Restaurant ID", example = "1", required = true) restaurantId: Long?,
        @ApiParam(value = "User ID", example = "1", required = true) userId: Long?
    ): ResponseEntity<Void>

    @ApiOperation("Restaurant association with responsible user")
    @ApiResponses(
        *[
            ApiResponse(code = 204, message = "Successful association"),
            ApiResponse(code = 404, message = "Restaurant or user not found", response = Problem::class)
        ]
    )
    fun associate(
        @ApiParam(value = "Restaurant ID", example = "1", required = true) restaurantId: Long?,
        @ApiParam(value = "User ID", example = "1", required = true) userId: Long?
    ): ResponseEntity<Void>

}