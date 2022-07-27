package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response

import io.swagger.annotations.ApiModelProperty
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation

@Relation(collectionRelation = "cities")
data class CityModelResponse(

    @ApiModelProperty(example = "1")
    var id: Long? = null,

    @ApiModelProperty(example = "Petrópolis")
    var name: String? = null,

    var state: StateModelResponse? = null

) : RepresentationModel<CityModelResponse>()