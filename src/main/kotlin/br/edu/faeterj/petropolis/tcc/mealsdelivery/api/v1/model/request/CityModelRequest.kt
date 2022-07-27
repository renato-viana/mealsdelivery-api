package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request

import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.validation.NoArg
import io.swagger.annotations.ApiModelProperty
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@NoArg
data class CityModelRequest(

    @ApiModelProperty(example = "Petrópolis", required = true)
    @field:NotBlank
    val name: String,

    @field:Valid
    @field:NotNull
    val state: StateIdModelRequest

)