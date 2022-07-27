package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.model

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.PaymentMethodModelResponse
import io.swagger.annotations.ApiModel
import org.springframework.hateoas.Links

@ApiModel("PaymentMethodsModelResponseOpenApi")
class PaymentMethodsModelResponseOpenApi(

    var _embedded: PaymentMethodsEmbeddedModelResponseOpenApi? = null,

    var _links: Links? = null

) {

    @ApiModel("PaymentMethodsEmbeddedModelResponse")
    companion object PaymentMethodsEmbeddedModelResponseOpenApi {

        var paymentMethod: List<PaymentMethodModelResponse>? = null

    }

}