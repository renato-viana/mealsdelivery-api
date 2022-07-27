package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.model

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.CuisineModelResponse
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.math.BigDecimal

@ApiModel("RestaurantSummaryModelOpenApi")
class RestaurantSummaryModelResponseOpenApi(

    @ApiModelProperty(example = "1")
    private val id: Long? = null,

    @ApiModelProperty(example = "Thai Gourmet")
    private val name: String? = null,

    @ApiModelProperty(example = "12.00")
    private val deliveryFee: BigDecimal? = null,

    private val cuisine: CuisineModelResponse? = null

)