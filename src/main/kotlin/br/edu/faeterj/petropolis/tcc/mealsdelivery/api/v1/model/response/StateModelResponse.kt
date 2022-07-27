package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response

import io.swagger.annotations.ApiModelProperty
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation

@Relation(collectionRelation = "states")
data class StateModelResponse(

    @ApiModelProperty(example = "1")
    var id: Long? = null,

    @ApiModelProperty(example = "Rio de Janeiro")
    var name: String

) : RepresentationModel<StateModelResponse>()
