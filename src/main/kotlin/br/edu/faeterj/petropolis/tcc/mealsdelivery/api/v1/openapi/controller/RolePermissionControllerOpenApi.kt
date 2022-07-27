package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.exceptionhandler.Problem
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.PermissionModelResponse
import io.swagger.annotations.*
import org.springframework.hateoas.CollectionModel
import org.springframework.http.ResponseEntity

@Api(tags = ["Roles"])
interface RolePermissionControllerOpenApi {

    @ApiOperation("Lists the permissions associated with a role")
    @ApiResponses(
        *[
            ApiResponse(code = 400, message = "Invalid role ID", response = Problem::class),
            ApiResponse(code = 404, message = "Role not found", response = Problem::class)
        ]
    )
    fun list(
        @ApiParam(value = "Role ID", example = "1", required = true) roleId: Long?
    ): CollectionModel<PermissionModelResponse>

    @ApiOperation("Permission disassociation with role")
    @ApiResponses(
        *[
            ApiResponse(code = 204, message = "Successfully disassociated"),
            ApiResponse(code = 404, message = "Role or permission not found", response = Problem::class)
        ]
    )
    fun disassociate(
        @ApiParam(value = "ID do role", example = "1", required = true) roleId: Long?,
        @ApiParam(value = "Permission ID", example = "1", required = true) permissionId: Long?
    ): ResponseEntity<*>

    @ApiOperation("Permission association with role")
    @ApiResponses(
        *[
            ApiResponse(code = 204, message = "Successful association"),
            ApiResponse(code = 404, message = "Role or permission not found", response = Problem::class)
        ]
    )
    fun associate(
        @ApiParam(value = "ID do role", example = "1", required = true) roleId: Long?,
        @ApiParam(value = "Permission ID", example = "1", required = true) permissionId: Long?
    ): ResponseEntity<*>

}