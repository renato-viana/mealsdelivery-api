package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response

import io.swagger.annotations.ApiModelProperty
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation
import java.math.BigDecimal

@Relation(collectionRelation = "products")
data class ProductModelResponse(

    @ApiModelProperty(example = "1")
    var id: Long? = null,

    @ApiModelProperty(example = "Espetinho de Cupim")
    var name: String,

    @ApiModelProperty(example = "Acompanha farinha, mandioca e vinagrete")
    var description: String,

    @ApiModelProperty(example = "12.50")
    var price: BigDecimal,

    @ApiModelProperty(example = "true")
    var active: Boolean

) : RepresentationModel<ProductModelResponse>()