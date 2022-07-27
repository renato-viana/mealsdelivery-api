package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.PaymentMethodModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.PaymentMethodModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler.PaymentMethodModelResponseAssembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.disassembler.PaymentMethodModelRequestDisassembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller.PaymentMethodControllerOpenApi
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.CheckSecurity
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.PaymentMethod
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.PaymentMethodRepository
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.PaymentMethodService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.http.CacheControl
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.filter.ShallowEtagHeaderFilter
import java.time.OffsetDateTime
import java.util.concurrent.TimeUnit
import javax.validation.Valid

@RestController
@RequestMapping(path = ["/v1/payment-methods"])
class PaymentMethodController(

    @Autowired
    private val paymentMethodRepository: PaymentMethodRepository,

    @Autowired
    private val paymentMethodService: PaymentMethodService,

    @Autowired
    private val paymentMethodModelResponseAssembler: PaymentMethodModelResponseAssembler,

    @Autowired
    private val paymentMethodModelRequestDisassembler: PaymentMethodModelRequestDisassembler

) : PaymentMethodControllerOpenApi {

    @CheckSecurity.PaymentMethods.CanConsult
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun list(request: ServletWebRequest): ResponseEntity<CollectionModel<PaymentMethodModelResponse>>? {
        ShallowEtagHeaderFilter.disableContentCaching(request.request)

        var eTag = "0"
        val lastUpdateDate: OffsetDateTime? = paymentMethodRepository.getLastUpdateDate()

        if (lastUpdateDate != null) {
            eTag = lastUpdateDate.toEpochSecond().toString()
        }

        if (request.checkNotModified(eTag)) {
            return null
        }

        val paymentMethods: List<PaymentMethod> = paymentMethodRepository.findAll()

        val paymentMethodsModelResponse: CollectionModel<PaymentMethodModelResponse> =
            paymentMethodModelResponseAssembler.toCollectionModel(paymentMethods)

        return ResponseEntity.ok()
            .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
            .eTag(eTag)
            .body(paymentMethodsModelResponse)
    }

    @CheckSecurity.PaymentMethods.CanConsult
    @GetMapping(path = ["/{paymentMethodId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun fetch(
        @PathVariable paymentMethodId: Long?,
        request: ServletWebRequest?
    ): ResponseEntity<PaymentMethodModelResponse>? {
        ShallowEtagHeaderFilter.disableContentCaching(request!!.request)

        var eTag = "0"

        val updateDate: OffsetDateTime? = paymentMethodRepository.getUpdateDateById(paymentMethodId)

        if (updateDate != null) {
            eTag = updateDate.toEpochSecond().toString()
        }

        if (request.checkNotModified(eTag)) {
            return null
        }

        val paymentMethod: PaymentMethod = paymentMethodService.fetchOrFail(paymentMethodId)

        val paymentMethodOutputDTO: PaymentMethodModelResponse =
            paymentMethodModelResponseAssembler.toModel(paymentMethod)

        return ResponseEntity.ok()
            .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
            .eTag(eTag)
            .body(paymentMethodOutputDTO)
    }

    @CheckSecurity.PaymentMethods.CanEdit
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    override fun add(
        @RequestBody @Valid paymentMethodModelRequest: PaymentMethodModelRequest
    ): PaymentMethodModelResponse {
        var paymentMethod: PaymentMethod =
            paymentMethodModelRequestDisassembler.toDomainObject(paymentMethodModelRequest)
        paymentMethod = paymentMethodService.save(paymentMethod)
        return paymentMethodModelResponseAssembler.toModel(paymentMethod)
    }

    @CheckSecurity.PaymentMethods.CanEdit
    @PutMapping(path = ["/{paymentMethodId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun update(
        @PathVariable paymentMethodId: Long,
        @RequestBody @Valid paymentMethodModelRequest: PaymentMethodModelRequest
    ): PaymentMethodModelResponse {
        var currentPaymentMethod: PaymentMethod = paymentMethodService.fetchOrFail(paymentMethodId)
        paymentMethodModelRequestDisassembler.copyToDomainObject(paymentMethodModelRequest, currentPaymentMethod)
        currentPaymentMethod = paymentMethodService.save(currentPaymentMethod)
        return paymentMethodModelResponseAssembler.toModel(currentPaymentMethod)
    }

    @CheckSecurity.PaymentMethods.CanEdit
    @DeleteMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun remove(@PathVariable paymentMethodId: Long) {
        paymentMethodService.delete(paymentMethodId)
    }

}