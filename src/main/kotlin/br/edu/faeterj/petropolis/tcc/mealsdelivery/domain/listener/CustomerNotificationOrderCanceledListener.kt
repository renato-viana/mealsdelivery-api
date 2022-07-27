package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.listener

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.event.OrderCanceledEvent
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Order
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.SendEmailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class CustomerNotificationOrderCanceledListener(

    @Autowired
    private val sendEmailService: SendEmailService

) {

    @TransactionalEventListener
    fun cancelOrder(event: OrderCanceledEvent) {
        val order: Order = event.order

        val message: SendEmailService.Message = SendEmailService.Message(
            recipients = mutableSetOf(order.customer.email),
            subject = "${order.restaurant.name} - Pedido cancelado",
            body = "emails/order-canceled.html",
            variables = mutableMapOf("order" to order)
        )

        sendEmailService.send(message)
    }

}