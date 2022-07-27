package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request

import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.validation.NoArg
import io.swagger.annotations.ApiModelProperty
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@NoArg
data class AddressModelRequest(

    @ApiModelProperty(example = "25610-081", required = true)
    @field:NotBlank
    val zipCode: String,

    @ApiModelProperty(example = "Rua do Imperador", required = true)
    @field:NotBlank
    val street: String,

    @ApiModelProperty(example = "754", required = true)
    @field:NotBlank
    val number: String,

    @ApiModelProperty(example = "Perto da Igreja")
    val complement: String? = null,

    @ApiModelProperty(example = "Centro", required = true)
    @field:NotBlank
    val district: String,

    @field:Valid
    @field:NotNull
    val city: CityIdModelRequest

)