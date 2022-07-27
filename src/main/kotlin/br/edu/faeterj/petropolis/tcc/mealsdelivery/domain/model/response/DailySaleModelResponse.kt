package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.response

import java.math.BigDecimal
import java.util.*

data class DailySaleModelResponse(
    val date: Date,
    val salesAmount: Long,
    val totalBilling: BigDecimal,
)