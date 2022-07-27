package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller.StatisticsController.StatisticsModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.filter.DailySaleFilter
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.response.DailySaleModelResponse
import io.swagger.annotations.*
import org.springframework.http.ResponseEntity

@Api(tags = ["Statistics"])
interface StatisticsControllerOpenApi {

    @ApiOperation("Consult daily sales statistics")
    @ApiImplicitParams(
        *[
            ApiImplicitParam(
                name = "restaurantId",
                value = "Restaurant ID",
                example = "1",
                dataType = "Long"
            ), ApiImplicitParam(
                name = "creationDateStart",
                value = "Start date and time of order creation",
                example = "2019-12-01T00:00:00Z",
                dataType = "date-time"
            ), ApiImplicitParam(
                name = "creationDateEnd",
                value = "Final date and time of order creation",
                example = "2019-12-02T23:59:59Z",
                dataType = "date-time"
            )]
    )
    fun consultDailySales(
        filter: DailySaleFilter?,
        @ApiParam(
            value = "Time zone offset, specifies the zone offset from UTC for a datetime value.",
            defaultValue = "+00:00"
        ) timeOffset: String?
    ): List<DailySaleModelResponse?>?

    fun consultDailySalesPdf(filter: DailySaleFilter, timeOffset: String): ResponseEntity<ByteArray>

    @ApiOperation(value = "Statistics", hidden = true)
    fun statistics(): StatisticsModelResponse

}