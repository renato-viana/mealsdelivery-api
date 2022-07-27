package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model

import java.math.BigDecimal
import javax.persistence.*

@Entity
class OrderItem(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var amount: Int,

    var unitPrice: BigDecimal,

    var totalPrice: BigDecimal,

    var note: String? = null,

    @ManyToOne
    @JoinColumn(nullable = false)
    var order: Order,

    @ManyToOne
    @JoinColumn(nullable = false)
    var product: Product,
) {

    fun calculateTotalPrice() {
        totalPrice = unitPrice.multiply(BigDecimal(amount))
    }

}