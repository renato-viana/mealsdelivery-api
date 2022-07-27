package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.exceptionhandler.Problem
import io.swagger.annotations.*
import org.springframework.http.ResponseEntity

@Api(tags = ["Orders"])
interface OrderStatusControllerOpenApi {
    @ApiOperation("Order confirmation")
    @ApiResponses(
        *[
            ApiResponse(code = 204, message = " Order confirmed successfully"),
            ApiResponse(code = 404, message = "Order not found", response = Problem::class)
        ]
    )
    fun confirm(
        @ApiParam(
            value = "Order code",
            example = "f9981ca4-5a5e-4da3-af04-933861df3e55",
            required = true
        ) orderCode: String
    ): ResponseEntity<Void>

    @ApiOperation("Order cancellation")
    @ApiResponses(
        *[
            ApiResponse(code = 204, message = "Order canceled successfully"),
            ApiResponse(code = 404, message = "Order not found", response = Problem::class)
        ]
    )
    fun cancel(
        @ApiParam(
            value = "Order code",
            example = "f9981ca4-5a5e-4da3-af04-933861df3e55",
            required = true
        ) orderCode: String
    ): ResponseEntity<Void>

    @ApiOperation("Register order delivery")
    @ApiResponses(
        *[
            ApiResponse(code = 204, message = "Order delivered successfully"),
            ApiResponse(code = 404, message = "Order not found", response = Problem::class)
        ]
    )
    fun deliver(
        @ApiParam(
            value = "Order code",
            example = "f9981ca4-5a5e-4da3-af04-933861df3e55",
            required = true
        ) orderCode: String
    ): ResponseEntity<Void>

}