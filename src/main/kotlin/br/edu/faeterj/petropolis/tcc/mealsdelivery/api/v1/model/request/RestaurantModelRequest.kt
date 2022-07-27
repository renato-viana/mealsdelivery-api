package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request

import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.validation.NoArg
import io.swagger.annotations.ApiModelProperty
import java.math.BigDecimal
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

@NoArg
data class RestaurantModelRequest(

    @ApiModelProperty(example = "Churrascaria Majórica", required = true)
    @field:NotBlank
    val name: String,

    @ApiModelProperty(example = "10.00", required = true)
    @field:NotNull
    @field:PositiveOrZero
    val deliveryFee: BigDecimal,

    @field:Valid
    @field:NotNull
    val cuisine: CuisineIdModelRequest,

    @field:Valid
    @field:NotNull
    val address: AddressModelRequest

)