package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.filter

import io.swagger.annotations.ApiModelProperty
import org.springframework.format.annotation.DateTimeFormat
import java.time.OffsetDateTime

open class OrderFilter(

    @ApiModelProperty(example = "1", value = "ID do cliente para filtro da pesquisa")
    val customerId: Long? = null,

    @ApiModelProperty(example = "1", value = "ID do restaurante para filtro da pesquisa")
    val restaurantId: Long? = null,

    @ApiModelProperty(example = "2019-10-30T00:00:00Z", value = "Data/hora de criação inicial para filtro da pesquisa")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val creationDateStart: OffsetDateTime? = null,

    @ApiModelProperty(example = "2019-11-01T10:00:00Z", value = "Data/hora de criação final para filtro da pesquisa")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val creationDateEnd: OffsetDateTime? = null

)