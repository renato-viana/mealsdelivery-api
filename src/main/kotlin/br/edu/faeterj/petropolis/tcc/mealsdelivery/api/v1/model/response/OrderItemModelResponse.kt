package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response

import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.validation.NoArg
import io.swagger.annotations.ApiModelProperty
import org.springframework.hateoas.RepresentationModel
import java.math.BigDecimal

@NoArg
data class OrderItemModelResponse(

    @ApiModelProperty(example = "1")
    var productId: Long? = null,

    @ApiModelProperty(example = "Filé Mignon")
    var productName: String,

    @ApiModelProperty(example = "1")
    var amount: Int,

    @ApiModelProperty(example = "171.10")
    var unitPrice: BigDecimal,

    @ApiModelProperty(example = "171.10")
    var totalPrice: BigDecimal,

    @ApiModelProperty(example = "Bem-passado")
    var note: String? = null

) : RepresentationModel<OrderItemModelResponse>()