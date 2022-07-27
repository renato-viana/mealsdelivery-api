package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.event.OrderCanceledEvent
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.event.OrderConfirmedEvent
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.BusinessException
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.domain.AbstractAggregateRoot
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*
import javax.persistence.*

@Entity
@javax.persistence.Table(name = "\"Order\"")
class Order(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var code: String,

    var subtotal: BigDecimal,

    var deliveryFee: BigDecimal,

    var totalPrice: BigDecimal,

    @CreationTimestamp
    var creationDate: OffsetDateTime,

    var confirmationDate: OffsetDateTime,

    var cancellationDate: OffsetDateTime,

    var deliveryDate: OffsetDateTime,

    @ManyToOne
    @JoinColumn(nullable = false)
    var restaurant: Restaurant,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    var paymentMethod: PaymentMethod,

    @ManyToOne
    @JoinColumn(name = "user_customer_id", nullable = false)
    var customer: User,

    @Enumerated(EnumType.STRING)
    var status: OrderStatus,

    @Embedded
    var deliveryAddress: Address,

    @Column(nullable = false)
    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    var items: MutableList<OrderItem> = mutableListOf()

) : AbstractAggregateRoot<Order>() {

    fun calculateTotalPrice() {
        items.forEach(OrderItem::calculateTotalPrice)

        subtotal = items.map { it.totalPrice }
            .fold(BigDecimal.ZERO, BigDecimal::add)

        totalPrice = subtotal.add(deliveryFee)
    }

    fun confirm() {
        setNewStatus(OrderStatus.CONFIRMED)
        confirmationDate = OffsetDateTime.now()

        registerEvent(OrderConfirmedEvent(this))
    }

    fun deliver() {
        setNewStatus(OrderStatus.DELIVERED)
        deliveryDate = OffsetDateTime.now()
    }

    fun cancel() {
        setNewStatus(OrderStatus.CANCELLED)
        cancellationDate = OffsetDateTime.now()

        registerEvent(OrderCanceledEvent(this))
    }

    fun canBeConfirmed(): Boolean {
        return status.canChangeTo(OrderStatus.CONFIRMED)
    }

    fun canBeDelivered(): Boolean {
        return status.canChangeTo(OrderStatus.DELIVERED)
    }

    fun canBeCancelled(): Boolean {
        return status.canChangeTo(OrderStatus.CANCELLED)
    }

    private fun setNewStatus(newStatus: OrderStatus) {
        if (status.cannotChangeTo(newStatus)) {
            throw BusinessException(
                "Status do pedido $code não pode ser alterado de ${status.description} para ${newStatus.description}"
            )
        }

        status = newStatus
    }

    @PrePersist
    private fun generateCode() {
        code = UUID.randomUUID().toString()
    }

}