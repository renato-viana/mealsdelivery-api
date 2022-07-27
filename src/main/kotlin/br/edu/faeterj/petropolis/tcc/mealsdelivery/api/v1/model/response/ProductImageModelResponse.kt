package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response

import io.swagger.annotations.ApiModelProperty
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation

@Relation(collectionRelation = "images")
data class ProductImageModelResponse(

    @ApiModelProperty(example = "1ae02f9a-e4cb-41d1-903f-55c6d6ebf1f1_file-mignon.jpg")
    var fileName: String,

    @ApiModelProperty(example = "Filé Mignon")
    var description: String,

    @ApiModelProperty(example = "image/jpeg")
    var contentType: String,

    @ApiModelProperty(example = "118275")
    var size: Long

) : RepresentationModel<ProductImageModelResponse>()