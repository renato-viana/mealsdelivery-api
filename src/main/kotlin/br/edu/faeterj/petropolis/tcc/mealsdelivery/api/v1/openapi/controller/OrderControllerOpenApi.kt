package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.exceptionhandler.Problem
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.OrderModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.OrderModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.OrderSummaryModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.filter.OrderFilter
import io.swagger.annotations.*
import org.springframework.data.domain.Pageable
import org.springframework.hateoas.PagedModel

@Api(tags = ["Orders"])
interface OrderControllerOpenApi {

    @ApiOperation("Search orders")
    @ApiImplicitParams(
        *[ApiImplicitParam(
            value = "Property names to filter on response, comma separated",
            name = "fields",
            paramType = "query",
            type = "string"
        )]
    )
    fun search(filter: OrderFilter?, pageable: Pageable): PagedModel<OrderSummaryModelResponse>

    @ApiOperation("Representation of a registered order")
    @ApiResponses(*[ApiResponse(code = 201, message = "Registered order")])
    fun add(
        @ApiParam(
            name = "Body",
            value = "Representation of a registered order",
            required = true
        ) orderModelRequest: OrderModelRequest
    ): OrderModelResponse

    @ApiImplicitParams(
        *[ApiImplicitParam(
            value = "Property names to filter on response, comma separated",
            name = "fields",
            paramType = "query",
            type = "string"
        )]
    )
    @ApiOperation("Fetch a order by code")
    @ApiResponses(*[ApiResponse(code = 404, message = "Order not found", response = Problem::class)])
    fun fetch(
        @ApiParam(
            value = "Order code",
            example = "f9981ca4-5a5e-4da3-af04-933861df3e55",
            required = true
        ) orderCode: String
    ): OrderModelResponse

}