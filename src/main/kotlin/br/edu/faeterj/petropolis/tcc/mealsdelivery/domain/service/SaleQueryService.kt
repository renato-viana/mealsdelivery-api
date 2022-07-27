package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.filter.DailySaleFilter
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.response.DailySaleModelResponse

interface SaleQueryService {

    fun consultDailySales(filter: DailySaleFilter?, timeOffset: String?): List<DailySaleModelResponse?>?

}
