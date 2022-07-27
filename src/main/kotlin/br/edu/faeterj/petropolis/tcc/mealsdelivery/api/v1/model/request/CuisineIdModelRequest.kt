package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request

import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.validation.NoArg
import javax.validation.constraints.NotNull

@NoArg
data class CuisineIdModelRequest(

    @field:NotNull
    val id: Long

)