package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response

import io.swagger.annotations.ApiModelProperty
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation

@Relation(collectionRelation = "cities")
data class CitySummaryModelResponse(

    @ApiModelProperty(example = "1")
    var id: Long? = null,

    @ApiModelProperty(example = "Petrópolis")
    var name: String,

    @ApiModelProperty(example = "Rio de Janeiro")
    var state: String

) : RepresentationModel<CitySummaryModelResponse>()