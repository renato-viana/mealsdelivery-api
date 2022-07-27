package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller.OrderStatusControllerOpenApi
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.CheckSecurity
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.OrderStatusService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/v1/orders/{orderCode}"])
class OrderStatusController(

    @Autowired
    private val orderStatusService: OrderStatusService

) : OrderStatusControllerOpenApi {

    @CheckSecurity.Orders.CanManageOrders
    @PutMapping("/confirmation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun confirm(@PathVariable orderCode: String): ResponseEntity<Void> {
        orderStatusService.confirm(orderCode)
        return ResponseEntity.noContent().build()
    }

    @CheckSecurity.Orders.CanManageOrders
    @PutMapping("/delivery")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun deliver(@PathVariable orderCode: String): ResponseEntity<Void> {
        orderStatusService.deliver(orderCode)
        return ResponseEntity.noContent().build()
    }

    @CheckSecurity.Orders.CanManageOrders
    @PutMapping("/cancellation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun cancel(@PathVariable orderCode: String): ResponseEntity<Void> {
        orderStatusService.cancel(orderCode)
        return ResponseEntity.noContent().build()
    }

}