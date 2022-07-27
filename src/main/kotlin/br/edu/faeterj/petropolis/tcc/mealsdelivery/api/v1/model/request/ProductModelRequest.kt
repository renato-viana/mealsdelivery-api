package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request

import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.validation.NoArg
import io.swagger.annotations.ApiModelProperty
import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

@NoArg
data class ProductModelRequest(

    @ApiModelProperty(example = "Filé Mignon", required = true)
    @field:NotBlank
    val name: String,

    @ApiModelProperty(
        example = "Delicioso Corte na Brasa com aprox. 800g, acompanha arroz, farofa, batata frita e salada.",
        required = true
    )
    @field:NotBlank
    val description: String,

    @ApiModelProperty(example = "171.10", required = true)
    @field:NotNull
    @field:PositiveOrZero
    val price: BigDecimal,

    @ApiModelProperty(example = "true", required = true)
    @field:NotNull
    val active: Boolean

)