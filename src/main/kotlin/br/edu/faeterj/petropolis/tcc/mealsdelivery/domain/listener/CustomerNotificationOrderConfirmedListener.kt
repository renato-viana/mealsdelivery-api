package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.listener

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.event.OrderConfirmedEvent
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Order
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.SendEmailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class CustomerNotificationOrderConfirmedListener(

    @Autowired
    private val sendEmailService: SendEmailService

) {

    @TransactionalEventListener
    fun confirmOrder(event: OrderConfirmedEvent) {
        val order: Order = event.order

        val message: SendEmailService.Message = SendEmailService.Message(
            recipients = mutableSetOf(order.customer.email),
            subject = "${order.restaurant.name} - Pedido confirmado",
            body = "emails/order-confirmed.html",
            variables = mutableMapOf("order" to order)
        )

        sendEmailService.send(message)
    }

}