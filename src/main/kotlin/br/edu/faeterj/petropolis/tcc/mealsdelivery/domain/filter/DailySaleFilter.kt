package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.filter

import org.springframework.format.annotation.DateTimeFormat
import java.time.OffsetDateTime

open class DailySaleFilter(

    val restaurantId: Long? = null,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val creationDateStart: OffsetDateTime? = null,

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val creationDateEnd: OffsetDateTime? = null

)