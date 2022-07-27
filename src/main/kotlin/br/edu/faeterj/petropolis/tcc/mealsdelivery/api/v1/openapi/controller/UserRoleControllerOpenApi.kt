package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.exceptionhandler.Problem
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.RoleModelResponse
import io.swagger.annotations.*
import org.springframework.hateoas.CollectionModel
import org.springframework.http.ResponseEntity

@Api(tags = ["Users"])
interface UserRoleControllerOpenApi {

    @ApiOperation("List the roles associated with a user")
    @ApiResponses(*[ApiResponse(code = 404, message = "User not found", response = Problem::class)])
    fun list(
        @ApiParam(value = "User ID", example = "1", required = true) userId: Long?
    ): CollectionModel<RoleModelResponse>

    @ApiOperation("Disassociation of role with user")
    @ApiResponses(
        *[
            ApiResponse(code = 204, message = "Successfully disassociated"),
            ApiResponse(code = 404, message = "User or role not found", response = Problem::class)
        ]
    )
    fun disassociate(
        @ApiParam(value = "User ID", example = "1", required = true) userId: Long?,
        @ApiParam(value = "Role ID", example = "1", required = true) roleId: Long?
    ): ResponseEntity<Void>

    @ApiOperation("User role association")
    @ApiResponses(
        *[
            ApiResponse(code = 204, message = "Successful association"),
            ApiResponse(code = 404, message = "User or role not found", response = Problem::class)
        ]
    )
    fun associate(
        @ApiParam(value = "User ID", example = "1", required = true) userId: Long?,
        @ApiParam(value = "Role ID", example = "1", required = true) roleId: Long?
    ): ResponseEntity<Void>

}