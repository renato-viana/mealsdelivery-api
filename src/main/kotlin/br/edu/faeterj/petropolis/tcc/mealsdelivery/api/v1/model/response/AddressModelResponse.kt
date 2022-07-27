package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response

import io.swagger.annotations.ApiModelProperty
import javax.persistence.Embeddable

@Embeddable
data class AddressModelResponse(

    @ApiModelProperty(example = "25610-081")
    var zipCode: String,

    @ApiModelProperty(example = "Rua do Imperador")
    var street: String,

    @ApiModelProperty(example = "754")
    var number: String,

    @ApiModelProperty(example = "Perto da Igreja")
    var complement: String? = null,

    @ApiModelProperty(example = "Centro")
    var district: String,

    var city: CitySummaryModelResponse? = null

)