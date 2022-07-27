package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request

import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.validation.NoArg
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotNull

@NoArg
data class CityIdModelRequest(

    @ApiModelProperty(example = "1", required = true)
    @field:NotNull
    val id: Long

)
