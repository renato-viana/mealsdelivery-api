package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model

enum class OrderStatus(var description: String, vararg previousStatus: OrderStatus) {

    CREATED("Created"),
    CONFIRMED("Confirmed", CREATED),
    DELIVERED("Delivered", CONFIRMED),
    CANCELLED("Cancelled", CREATED);

    private var previousStatus: MutableList<OrderStatus>

    fun cannotChangeTo(newStatus: OrderStatus): Boolean = !newStatus.previousStatus.contains(this)

    fun canChangeTo(newStatus: OrderStatus): Boolean {
        return !cannotChangeTo(newStatus)
    }

    init {
        this.previousStatus = mutableListOf(*previousStatus)
    }

}