package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response

import io.swagger.annotations.ApiModelProperty
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation
import java.math.BigDecimal

@Relation(collectionRelation = "restaurants")
data class RestaurantModelResponse(

    @ApiModelProperty(example = "1")
    var id: Long? = null,

    @ApiModelProperty(example = "Churrascaria Majórica")
    var name: String,

    @ApiModelProperty(example = "10.00")
    var deliveryFee: BigDecimal,

    var cuisine: CuisineModelResponse,

    var active: Boolean,

    var open: Boolean,

    var address: AddressModelResponse? = null

) : RepresentationModel<RestaurantModelResponse>()