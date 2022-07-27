package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.exceptionhandler.Problem
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.PaymentMethodModelResponse
import io.swagger.annotations.*
import org.springframework.hateoas.CollectionModel
import org.springframework.http.ResponseEntity

@Api(tags = ["Restaurants"])
interface RestaurantPaymentMethodControllerOpenApi {

    @ApiOperation("Lists payment methods associated with restaurant")
    @ApiResponses(*[ApiResponse(code = 404, message = "Restaurant not found", response = Problem::class)])
    fun list(
        @ApiParam(value = "Restaurant ID", example = "1", required = true) restaurantId: Long?
    ): CollectionModel<PaymentMethodModelResponse>

    @ApiOperation("Restaurant disassociation with payment method")
    @ApiResponses(
        *[
            ApiResponse(code = 204, message = "Successfully disassociated"),
            ApiResponse(
                code = 404,
                message = "Restaurant or payment method not found",
                response = Problem::class
            )
        ]
    )
    fun disassociate(
        @ApiParam(value = "Restaurant ID", example = "1", required = true) restaurantId: Long?,
        @ApiParam(value = "Payment Method ID", example = "1", required = true) paymentMethodId: Long?
    ): ResponseEntity<Void>

    @ApiOperation("Restaurant associated with the payment method")
    @ApiResponses(
        *[
            ApiResponse(code = 204, message = "Successful association"),
            ApiResponse(
                code = 404,
                message = "Restaurant or payment method not found",
                response = Problem::class
            )
        ]
    )
    fun associate(
        @ApiParam(value = "Restaurant ID", example = "1", required = true) restaurantId: Long?,
        @ApiParam(value = "Payment Method ID", example = "1", required = true) paymentMethodId: Long?
    ): ResponseEntity<Void>

}