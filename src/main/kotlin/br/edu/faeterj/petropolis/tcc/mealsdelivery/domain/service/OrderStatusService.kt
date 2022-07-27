package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Order
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderStatusService(

    @Autowired
    private val issuanceOrderService: IssuanceOrderService,

    @Autowired
    private val orderRepository: OrderRepository

) {

    @Transactional
    fun confirm(code: String) {
        val order: Order = issuanceOrderService.fetchOrFail(code)
        order.confirm()

        orderRepository.save(order)
    }

    @Transactional
    fun deliver(code: String) {
        val order: Order = issuanceOrderService.fetchOrFail(code)

        order.deliver()
    }

    @Transactional
    fun cancel(code: String) {
        val order: Order = issuanceOrderService.fetchOrFail(code)
        order.cancel()

        orderRepository.save(order)
    }

}