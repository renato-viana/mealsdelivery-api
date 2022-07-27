package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request

import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.validation.NoArg
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank

@NoArg
data class PasswordModelRequest(

    @ApiModelProperty(example = "123", required = true)
    @field:NotBlank
    val currentPassword: String,

    @ApiModelProperty(example = "123456", required = true)
    @field:NotBlank
    val newPassword: String

)