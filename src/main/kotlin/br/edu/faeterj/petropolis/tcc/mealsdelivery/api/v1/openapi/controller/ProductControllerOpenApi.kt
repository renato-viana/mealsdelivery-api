package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.exceptionhandler.Problem
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.ProductModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.ProductModelResponse
import io.swagger.annotations.*
import org.springframework.hateoas.CollectionModel

@Api(tags = ["Products"])
interface ProductControllerOpenApi {

    @ApiOperation("List of products")
    @ApiResponses(
        *[
            ApiResponse(code = 400, message = "Invalid product ID", response = Problem::class),
            ApiResponse(code = 404, message = "Product not found", response = Problem::class)
        ]
    )
    fun list(
        @ApiParam(value = "Product ID", example = "1", required = true) restaurantId: Long?,
        @ApiParam(
            value = "Indicates whether or not to include inactive products in the listing result",
            example = "false",
            defaultValue = "false"
        ) includeInactive: Boolean?
    ): CollectionModel<ProductModelResponse>

    @ApiOperation("Fetch a product by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 400, message = "Invalid restaurant ID or product ID", response = Problem::class),
            ApiResponse(code = 404, message = "Product not found", response = Problem::class)
        ]
    )
    fun fetch(
        @ApiParam(value = "Restaurant ID", example = "1", required = true) restaurantId: Long?,
        @ApiParam(value = "Product ID", example = "1", required = true) productId: Long?
    ): ProductModelResponse

    @ApiOperation("Register a product")
    @ApiResponses(
        *[
            ApiResponse(code = 201, message = "Registered product"),
            ApiResponse(code = 404, message = "Restaurant not found", response = Problem::class)
        ]
    )
    fun add(
        @ApiParam(value = "Restaurant ID", example = "1", required = true) restaurantId: Long,
        @ApiParam(
            name = "Body",
            value = "Representation of a registered product",
            required = true
        ) productModelRequest: ProductModelRequest
    ): ProductModelResponse

    @ApiOperation("Update a product by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 200, message = "Updated product"),
            ApiResponse(code = 404, message = "Product not found", response = Problem::class)
        ]
    )
    fun update(
        @ApiParam(value = "Restaurant ID", example = "1", required = true) restaurantId: Long,
        @ApiParam(value = "ID do product", example = "1", required = true) productId: Long,
        @ApiParam(
            name = "Body",
            value = "Representation of a product with updated data",
            required = true
        ) productModelRequest: ProductModelRequest
    ): ProductModelResponse

}