package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.model

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.OrderSummaryModelResponse
import io.swagger.annotations.ApiModel
import org.springframework.hateoas.Links

@ApiModel("OrdersSummaryModelResponseOpenApi")
class OrdersSummaryModelResponseOpenApi(

    private val _embedded: OrdersSummaryEmbeddedModelResponseOpenApi? = null,

    private val _links: Links? = null,

    private val page: PagedModelResponseOpenApi? = null

) {

    @ApiModel("OrdersSummaryEmbeddedModelResponse")
    companion object OrdersSummaryEmbeddedModelResponseOpenApi {

        private val orders: List<OrderSummaryModelResponse>? = null

    }

}