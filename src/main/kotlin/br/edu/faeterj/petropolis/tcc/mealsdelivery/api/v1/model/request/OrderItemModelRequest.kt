package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request

import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.validation.NoArg
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

@NoArg
data class OrderItemModelRequest(

    @ApiModelProperty(example = "1", required = true)
    @field:NotNull
    val productId: Long,

    @ApiModelProperty(example = "1", required = true)
    @field:NotNull
    @field:PositiveOrZero
    val amount: Int,

    @ApiModelProperty(example = "Bem-passado")
    val note: String? = null

)