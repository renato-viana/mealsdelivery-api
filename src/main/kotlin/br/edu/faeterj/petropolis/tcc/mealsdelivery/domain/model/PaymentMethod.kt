package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model

import org.hibernate.annotations.UpdateTimestamp
import java.time.OffsetDateTime
import javax.persistence.*

@Entity
class PaymentMethod(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var description: String,

    @UpdateTimestamp
    private val registrationUpdateDate: OffsetDateTime

)