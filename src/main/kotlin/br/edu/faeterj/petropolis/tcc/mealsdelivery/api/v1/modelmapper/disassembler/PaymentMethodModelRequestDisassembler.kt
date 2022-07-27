package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.disassembler

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.PaymentMethodModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.PaymentMethod
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class PaymentMethodModelRequestDisassembler(

    @Autowired
    private val modelMapper: ModelMapper? = null

) {

    fun toDomainObject(paymentMethodModelRequest: PaymentMethodModelRequest): PaymentMethod {
        return modelMapper!!.map(paymentMethodModelRequest, PaymentMethod::class.java)
    }

    fun copyToDomainObject(paymentMethodModelRequest: PaymentMethodModelRequest?, paymentMethod: PaymentMethod?) {
        modelMapper?.map(paymentMethodModelRequest, paymentMethod)
    }

}