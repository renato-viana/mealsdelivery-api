package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.exceptionhandler.Problem
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.PaymentMethodModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.PaymentMethodModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.model.PaymentMethodsModelResponseOpenApi
import io.swagger.annotations.*
import org.springframework.hateoas.CollectionModel
import org.springframework.http.ResponseEntity
import org.springframework.web.context.request.ServletWebRequest

@Api(tags = ["Payment Methods"])
interface PaymentMethodControllerOpenApi {

    @ApiOperation(value = "List of payment methods", response = PaymentMethodsModelResponseOpenApi::class)
    fun list(request: ServletWebRequest): ResponseEntity<CollectionModel<PaymentMethodModelResponse>>?

    @ApiOperation("Fetch a payment method by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 400, message = "Invalid payment method ID", response = Problem::class),
            ApiResponse(code = 404, message = "Payment method not found", response = Problem::class)
        ]
    )
    fun fetch(
        @ApiParam(value = "Payment method ID", example = "1", required = true) paymentMethodId: Long?,
        request: ServletWebRequest?
    ): ResponseEntity<PaymentMethodModelResponse>?

    @ApiOperation("Register a payment method")
    @ApiResponses(*[ApiResponse(code = 201, message = "Registered payment method")])
    fun add(
        @ApiParam(
            name = "Body",
            value = "Representation of a registered payment method",
            required = true
        ) paymentMethodModelRequest: PaymentMethodModelRequest
    ): PaymentMethodModelResponse

    @ApiOperation("Update a payment method by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 200, message = "Updated payment method"),
            ApiResponse(code = 404, message = "Payment method not found", response = Problem::class)
        ]
    )
    fun update(
        @ApiParam(value = "ID de uma payment method", example = "1", required = true) paymentMethodId: Long,
        @ApiParam(
            name = "Body",
            value = "Representation of a payment method with updated data",
            required = true
        ) paymentMethodModelRequest: PaymentMethodModelRequest
    ): PaymentMethodModelResponse

    @ApiOperation("Delete a payment method by ID")
    @ApiResponses(
        *[
            ApiResponse(code = 204, message = "Payment method deleted"),
            ApiResponse(code = 404, message = "Payment method not found", response = Problem::class)
        ]
    )
    fun remove(
        @ApiParam(value = "Payment method ID", example = "1", required = true) paymentMethodId: Long
    )

}