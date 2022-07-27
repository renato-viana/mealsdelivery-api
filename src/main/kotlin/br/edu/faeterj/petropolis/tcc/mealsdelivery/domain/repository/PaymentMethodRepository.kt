package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.PaymentMethod
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
interface PaymentMethodRepository : JpaRepository<PaymentMethod, Long> {

    @Query("SELECT max(registrationUpdateDate) FROM PaymentMethod")
    fun getLastUpdateDate(): OffsetDateTime?

    @Query("SELECT registrationUpdateDate FROM PaymentMethod WHERE id = :paymentMethodId")
    fun getUpdateDateById(paymentMethodId: Long?): OffsetDateTime

}