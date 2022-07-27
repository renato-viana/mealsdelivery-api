package br.edu.faeterj.petropolis.tcc.mealsdelivery.infrastructure.service.report

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.filter.DailySaleFilter
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.response.DailySaleModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.SaleQueryService
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.SalesJasperReportsService
import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.JasperPrint
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class SalesJasperReportsServiceImpl(

    @Autowired
    private val saleQueryService: SaleQueryService

) : SalesJasperReportsService {

    override fun issueDailySales(filter: DailySaleFilter?, timeOffset: String?): ByteArray? {
        return try {
            val inputStream = this.javaClass.getResourceAsStream("/reports/daily-sales.jasper")

            val parameters = HashMap<String, Any>()
            parameters["REPORT_LOCALE"] = Locale("pt", "BR")

            val dailySaleModelResponses: List<DailySaleModelResponse?>? = saleQueryService.consultDailySales(filter, timeOffset)
            val dataSource = JRBeanCollectionDataSource(dailySaleModelResponses)

            val jasperPrint: JasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource)

            JasperExportManager.exportReportToPdf(jasperPrint)
        } catch (e: Exception) {
            throw ReportException("Não foi possível emitir o relatório das vendas diárias", e)
        }
    }

}