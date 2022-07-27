package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response

import io.swagger.annotations.ApiModelProperty
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation
import java.math.BigDecimal
import java.time.OffsetDateTime

@Relation(collectionRelation = "orders")
data class OrderSummaryModelResponse(

    @ApiModelProperty(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
    var code: String? = null,

    @ApiModelProperty(example = "574.30")
    var subtotal: BigDecimal,

    @ApiModelProperty(example = "10.00")
    var deliveryFee: BigDecimal,

    @ApiModelProperty(example = "584.30")
    var totalPrice: BigDecimal,

    @ApiModelProperty(example = "CREATED")
    var status: String,

    @ApiModelProperty(example = "2022-07-14T11:34:04Z")
    var creationDate: OffsetDateTime,

    var restaurant: RestaurantNameOnlyModelResponse,

    var customer: UserModelResponse

) : RepresentationModel<OrderSummaryModelResponse>()