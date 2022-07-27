package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.EntityInUseException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.PaymentMethodNotFoundException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.PaymentMethod
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.PaymentMethodRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PaymentMethodService(

    @Autowired
    private val paymentMethodRepository: PaymentMethodRepository,

    private val MSG_PAYMENT_METHOD_IN_USE: String = "Forma de pagamento de código %d não pode ser removida, pois está" +
            " em uso!"

) {
    @Transactional
    fun save(paymentMethod: PaymentMethod): PaymentMethod = paymentMethodRepository.save(paymentMethod)

    @Transactional
    fun delete(paymentMethodId: Long) {
        try {
            paymentMethodRepository.deleteById(paymentMethodId)
            paymentMethodRepository.flush()
        } catch (e: EmptyResultDataAccessException) {
            throw PaymentMethodNotFoundException(paymentMethodId)
        } catch (e: DataIntegrityViolationException) {
            throw EntityInUseException(String.format(MSG_PAYMENT_METHOD_IN_USE, paymentMethodId))
        }
    }

    fun fetchOrFail(paymentMethodId: Long?): PaymentMethod =
        paymentMethodRepository.findByIdOrNull(paymentMethodId) ?: throw PaymentMethodNotFoundException(paymentMethodId)

}