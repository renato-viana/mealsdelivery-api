package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.exceptionhandler.Problem
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.ProductImageModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.ProductImageModelResponse
import io.swagger.annotations.*
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@Api(tags = ["Products"])
interface ProductImageControllerOpenApi {

    @ApiOperation(
        value = "Fetch a product image",
        produces = "application/json, image/jpeg, image/png"
    )
    @ApiResponses(
        *[
            ApiResponse(code = 400, message = "Invalid restaurant ID or product ID", response = Problem::class),
            ApiResponse(code = 404, message = "Product image not found", response = Problem::class)
        ]
    )
    fun fetch(
        @ApiParam(value = "Restaurant ID", example = "1", required = true) restaurantId: Long?,
        @ApiParam(value = "Product ID", example = "1", required = true) productId: Long?
    ): ProductImageModelResponse

    @ApiOperation(value = "Fetch a product image", hidden = true)
    @Throws(
        HttpMediaTypeNotAcceptableException::class
    )
    fun fetchImage(restaurantId: Long, productId: Long, acceptHeader: String): ResponseEntity<*>

    @ApiOperation("Update a product image")
    @ApiResponses(
        *[
            ApiResponse(code = 200, message = "Updated product image"),
            ApiResponse(code = 404, message = "Product not found", response = Problem::class)
        ]
    )
    @Throws(
        IOException::class
    )
    fun updateImage(
        @ApiParam(value = "Restaurant ID", example = "1", required = true) restaurantId: Long,
        @ApiParam(value = "Product ID", example = "1", required = true) productId: Long,
        productImageModelRequest: ProductImageModelRequest,
        @ApiParam(
            value = "Product image file (maximum 500KB, JPG and PNG only)",
            required = true
        ) file: MultipartFile
    ): ProductImageModelResponse

    @ApiOperation("Delete a product image")
    @ApiResponses(
        *[
            ApiResponse(code = 204, message = "Product image deleted"),
            ApiResponse(code = 400, message = "Invalid restaurant ID or product ID", response = Problem::class),
            ApiResponse(code = 404, message = "Product image not found", response = Problem::class)
        ]
    )
    fun remove(
        @ApiParam(value = "Restaurant ID", example = "1", required = true) restaurantId: Long,
        @ApiParam(value = "Product ID", example = "1", required = true) productId: Long
    )

}