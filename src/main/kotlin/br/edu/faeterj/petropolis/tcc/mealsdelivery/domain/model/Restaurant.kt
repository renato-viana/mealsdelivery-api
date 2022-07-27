package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.OffsetDateTime
import javax.persistence.*

@Entity
class Restaurant(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var name: String = "",

    @Column(name = "delivery_fee", nullable = false)
    var deliveryFee: BigDecimal? = null,

    @ManyToOne
    @JoinColumn(name = "cuisine_id", nullable = false)
    var cuisine: Cuisine = Cuisine(),

    @Embedded
    var address: Address,
    var active: Boolean = true,
    var open: Boolean = false,

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    var registrationDate: OffsetDateTime = OffsetDateTime.now(),

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    var registrationUpdateDate: OffsetDateTime? = null,

    @ManyToMany
    @JoinTable(
        name = "restaurant_paymentMethod",
        joinColumns = [JoinColumn(name = "restaurant_id")],
        inverseJoinColumns = [JoinColumn(name = "paymentMethod_id")]
    )
    var paymentMethods: MutableSet<PaymentMethod> = mutableSetOf(),

    @ManyToMany
    @JoinTable(
        name = "restaurant_responsible_user",
        joinColumns = [JoinColumn(name = "restaurant_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    var responsible: MutableSet<User> = mutableSetOf(),

    @OneToMany(mappedBy = "restaurant")
    private val products: List<Product> = ArrayList<Product>()

) {

    fun activate() {
        active = true
    }

    fun inactivate() {
        active = false
    }

    fun open() {
        open = true
    }

    fun close() {
        open = false
    }

    fun removePaymentMethod(paymentMethod: PaymentMethod): Boolean = paymentMethods.remove(paymentMethod)

    fun addPaymentMethod(paymentMethod: PaymentMethod): Boolean = paymentMethods.add(paymentMethod)

    fun acceptPaymentMethod(paymentMethod: PaymentMethod): Boolean = paymentMethods.contains(paymentMethod)

    fun doesNotAcceptPaymentMethod(paymentMethod: PaymentMethod): Boolean = !acceptPaymentMethod(paymentMethod)

    fun removeResponsible(user: User): Boolean = responsible.remove(user)

    fun addResponsible(user: User): Boolean = responsible.add(user)

    fun isOpen(): Boolean {
        return this.open
    }

    fun isClosed(): Boolean {
        return !isOpen()
    }

    fun isInactive(): Boolean {
        return !isActive()
    }

    fun isActive(): Boolean {
        return this.active
    }

    fun allowedOpening(): Boolean {
        return isActive() && isClosed()
    }

    fun allowedActivation(): Boolean {
        return isInactive()
    }

    fun allowedInactivation(): Boolean {
        return isActive()
    }

    fun closingAllowed(): Boolean {
        return isOpen()
    }

}
