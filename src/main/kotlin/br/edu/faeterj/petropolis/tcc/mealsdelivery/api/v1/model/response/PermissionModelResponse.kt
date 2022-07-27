package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response

import io.swagger.annotations.ApiModelProperty
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation

@Relation(collectionRelation = "permissions")
data class PermissionModelResponse(

    @ApiModelProperty(example = "1")
    var id: Long? = null,

    @ApiModelProperty(example = "CONSULTAR_COZINHAS")
    var name: String,

    @ApiModelProperty(example = "Permite consultar cozinhas")
    var description: String

) : RepresentationModel<PermissionModelResponse>()