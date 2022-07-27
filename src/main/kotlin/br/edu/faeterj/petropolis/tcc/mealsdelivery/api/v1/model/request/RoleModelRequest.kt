package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request

import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.validation.NoArg
import javax.validation.constraints.NotBlank

@NoArg
data class RoleModelRequest(

    @field:NotBlank
    val name: @NotBlank String

)