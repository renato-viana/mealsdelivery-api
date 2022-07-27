package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.exceptionhandler.Problem
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.RoleModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.RoleModelResponse
import io.swagger.annotations.*
import org.springframework.hateoas.CollectionModel

@Api(tags = ["Roles"])
interface RoleControllerOpenApi {

    @ApiOperation("List of cities")
    fun list(): CollectionModel<RoleModelResponse>

    @ApiOperation("Fetch a role by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 400, message = "Invalid role ID", response = Problem::class),
            ApiResponse(code = 404, message = "Role not found", response = Problem::class)
        ]
    )
    fun search(@ApiParam(value = "Role ID", example = "1", required = true) roleId: Long): RoleModelResponse

    @ApiOperation("Register a role")
    @ApiResponses(*[ApiResponse(code = 201, message = "Registered role")])
    fun add(
        @ApiParam(
            name = "Body",
            value = "Representation of a registered role",
            required = true
        ) roleModelRequest: RoleModelRequest
    ): RoleModelResponse

    @ApiOperation("Update a role by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 200, message = "Updated role"),
            ApiResponse(code = 404, message = "Role not found", response = Problem::class)
        ]
    )
    fun update(
        @ApiParam(value = "Role ID", example = "1", required = true) roleId: Long,
        @ApiParam(
            name = "Body",
            value = "Representation of a role with updated data",
            required = true
        ) roleModelRequest: RoleModelRequest
    ): RoleModelResponse

    @ApiOperation("Delete a role by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 204, message = "Role deleted"),
            ApiResponse(code = 404, message = "Role not found", response = Problem::class)
        ]
    )
    fun remove(@ApiParam(value = "Role ID", example = "1", required = true) roleId: Long)

}