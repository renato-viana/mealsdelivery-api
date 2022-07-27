package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response

import io.swagger.annotations.ApiModelProperty
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation

@Relation(collectionRelation = "users")
data class UserModelResponse(

    @ApiModelProperty(example = "1")
    var id: Long? = null,

    @ApiModelProperty(example = "Renato Borges Viana")
    var name: String,

    @ApiModelProperty(example = "rviana@faeterj-petropolis.edu.br")
    var email: String

) : RepresentationModel<UserModelResponse>()