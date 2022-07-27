package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request

import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.validation.NoArg
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@NoArg
data class OrderModelRequest(

    @field:Valid
    @field:NotNull
    val restaurant: RestaurantIdModelRequest,

    @field:Valid
    @field:NotNull
    val deliveryAddress: AddressModelRequest,

    @field:Valid
    @field:NotNull
    val paymentMethod: PaymentMethodIdModelRequest,

    @field:Valid
    @field:Size(min = 1)
    @field:NotNull
    val items: MutableList<OrderItemModelRequest>

)