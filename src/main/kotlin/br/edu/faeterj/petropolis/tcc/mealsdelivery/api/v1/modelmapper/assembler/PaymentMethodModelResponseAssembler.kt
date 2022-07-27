package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller.PaymentMethodController
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.helper.ResourceLinkHelper
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.PaymentMethodModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.Security
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.PaymentMethod
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.stereotype.Component

@Component
class PaymentMethodModelResponseAssembler(

    @Autowired
    private val modelMapper: ModelMapper,

    @Autowired
    private val resourceLinkHelper: ResourceLinkHelper,

    @Autowired
    private val security: Security

) : RepresentationModelAssemblerSupport<PaymentMethod, PaymentMethodModelResponse>(
    PaymentMethodController::class.java, PaymentMethodModelResponse::class.java
) {

    override fun toModel(paymentMethod: PaymentMethod): PaymentMethodModelResponse {
        val paymentMethodModelResponse: PaymentMethodModelResponse =
            createModelWithId(paymentMethod.id ?: -1L, paymentMethod)

        modelMapper.map(paymentMethod, paymentMethodModelResponse)

        if (security.canConsultPaymentMethods()) {
            paymentMethodModelResponse.add(resourceLinkHelper.linkToPaymentMethods("paymentMethods"))
        }

        return paymentMethodModelResponse
    }

    override fun toCollectionModel(entities: Iterable<PaymentMethod?>): CollectionModel<PaymentMethodModelResponse> {
        val collectionModelResponse: CollectionModel<PaymentMethodModelResponse> = super.toCollectionModel(entities)

        if (security.canConsultPaymentMethods()) {
            collectionModelResponse.add(resourceLinkHelper.linkToPaymentMethods())
        }

        return collectionModelResponse
    }

}