package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.helper.ResourceLinkHelper
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller.StatisticsControllerOpenApi
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.CheckSecurity
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.filter.DailySaleFilter
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.response.DailySaleModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.SaleQueryService
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.SalesJasperReportsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.RepresentationModel
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/v1/statistics"])
class StatisticsController(

    @Autowired
    private val saleQueryService: SaleQueryService,

    @Autowired
    private val salesJasperReportsService: SalesJasperReportsService,

    @Autowired
    private val resourceLinkHelper: ResourceLinkHelper

) : StatisticsControllerOpenApi {

    @CheckSecurity.Statistics.CanConsult
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun statistics(): StatisticsModelResponse {
        val statisticsModelResponse = StatisticsModelResponse()
        statisticsModelResponse.add(resourceLinkHelper.linkToStatisticsDailySales("daily-sales"))
        return statisticsModelResponse
    }

    @CheckSecurity.Statistics.CanConsult
    @GetMapping(path = ["daily-sales"], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun consultDailySales(
        filter: DailySaleFilter?,
        @RequestParam(required = false, defaultValue = "+00:00") timeOffset: String?
    ): List<DailySaleModelResponse?>? {
        return saleQueryService.consultDailySales(filter, timeOffset)
    }

    @CheckSecurity.Statistics.CanConsult
    @GetMapping(path = ["daily-sales"], produces = [MediaType.APPLICATION_PDF_VALUE])
    override fun consultDailySalesPdf(
        filter: DailySaleFilter,
        @RequestParam(required = false, defaultValue = "+00:00") timeOffset: String
    ): ResponseEntity<ByteArray> {
        val bytesPdf: ByteArray? = salesJasperReportsService.issueDailySales(filter, timeOffset)

        val headers = HttpHeaders()
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=daily-sales.pdf")

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .headers(headers)
            .body<ByteArray>(bytesPdf)
    }

    class StatisticsModelResponse : RepresentationModel<StatisticsModelResponse>()

}