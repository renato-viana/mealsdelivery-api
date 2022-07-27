package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.model

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("PagedModelResponseOpenApi")
class PagedModelResponseOpenApi(

    @ApiModelProperty(example = "10", value = "Quantidade de registros por página")
    private val size: Long? = null,

    @ApiModelProperty(example = "50", value = "Total de registros")
    private val totalElements: Long? = null,

    @ApiModelProperty(example = "5", value = "Total de páginas")
    private val totalPages: Long? = null,

    @ApiModelProperty(example = "0", value = "Número da página (começa em 0)")
    private val number: Long? = null

)