package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.model

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("PageableModelResponseOpenApi")
class PageableModelResponseOpenApi(

    @ApiModelProperty(example = "0", value = "Número da página (começa em 0)")
    private val page: Int = 0,

    @ApiModelProperty(example = "10", value = "Quantidade de elementos por página")
    private val size: Int = 0,

    @ApiModelProperty(example = "nome,asc", value = "Nome da propriedade para ordenação")
    private val sort: List<String>? = null

)

