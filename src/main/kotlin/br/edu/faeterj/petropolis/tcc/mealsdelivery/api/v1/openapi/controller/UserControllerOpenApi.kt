package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.exceptionhandler.Problem
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.PasswordModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.UserModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.UserWithPasswordModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.UserModelResponse
import io.swagger.annotations.*
import org.springframework.hateoas.CollectionModel

@Api(tags = ["Users"])
interface UserControllerOpenApi {

    @ApiOperation("List of users")
    fun list(): CollectionModel<UserModelResponse>

    @ApiOperation("Fetch a user by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 400, message = "Invalid user ID", response = Problem::class),
            ApiResponse(code = 404, message = "User not found", response = Problem::class)
        ]
    )
    fun fetch(
        @ApiParam(value = "User ID", example = "1", required = true) userId: Long?
    ): UserModelResponse

    @ApiOperation("Register a user")
    @ApiResponses(*[ApiResponse(code = 201, message = "Registered user")])
    fun add(
        @ApiParam(
            name = "Body",
            value = "Representation of a registered user",
            required = true
        ) userWithPasswordModelRequest: UserWithPasswordModelRequest
    ): UserModelResponse

    @ApiOperation("Update a user by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 200, message = "Updated user"),
            ApiResponse(code = 404, message = "User not found", response = Problem::class)
        ]
    )
    fun update(
        @ApiParam(value = "User ID", example = "1", required = true) userId: Long,
        @ApiParam(
            name = "Body",
            value = "Representation of a user with updated data",
            required = true
        ) userModelRequest: UserModelRequest
    ): UserModelResponse

    @ApiOperation("Update a user's password")
    @ApiResponses(
        *[
            ApiResponse(code = 204, message = "Password changed successfully"),
            ApiResponse(code = 404, message = "User not found", response = Problem::class)
        ]
    )
    fun changePassword(
        @ApiParam(value = "User ID", example = "1", required = true) userId: Long,
        @ApiParam(
            name = "Body",
            value = "Representation of a new password",
            required = true
        ) passwordModelRequest: PasswordModelRequest
    )

}