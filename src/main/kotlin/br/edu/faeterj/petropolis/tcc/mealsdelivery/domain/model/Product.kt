package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model

import java.math.BigDecimal
import javax.persistence.*

@Entity
class Product(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false)
    var description: String? = null,

    @Column(nullable = false)
    var price: BigDecimal = BigDecimal(0),

    @Column(nullable = false)
    var active: Boolean = false,

    @ManyToOne
    @JoinColumn(nullable = false)
    var restaurant: Restaurant

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}