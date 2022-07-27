package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request

import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.validation.NoArg
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@NoArg
open class UserModelRequest(

    @ApiModelProperty(example = "Renato Borges Viana", required = true)
    @field:NotBlank
    var name: String? = null,

    @ApiModelProperty(example = "rviana@faeterj-petropolis.edu.br", required = true)
    @field:Email
    @field:NotBlank
    var email: String? = null

)