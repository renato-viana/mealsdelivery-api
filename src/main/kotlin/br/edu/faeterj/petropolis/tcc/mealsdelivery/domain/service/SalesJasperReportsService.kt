package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.filter.DailySaleFilter

interface SalesJasperReportsService {

    fun issueDailySales(filter: DailySaleFilter?, timeOffset: String?): ByteArray?

}
