package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.disassembler

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.OrderModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Order
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OrderModelRequestDisassembler(

    @Autowired
    private val modelMapper: ModelMapper? = null

) {

    fun toDomainObject(orderModelRequest: OrderModelRequest): Order {
        return modelMapper!!.map(orderModelRequest, Order::class.java)
    }

    fun copyToDomainObject(orderModelRequest: OrderModelRequest, order: Order) {
        modelMapper?.map(orderModelRequest, order)
    }

}