package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request

import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.validation.NoArg
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank

@NoArg
data class CuisineModelRequest(

    @ApiModelProperty(example = "Brasileira", required = true)
    @field:NotBlank
    var name: String? = null

)