package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.OrderModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.OrderModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.OrderSummaryModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler.OrderModelResponseAssembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler.OrderSummaryModelResponseAssembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.disassembler.OrderModelRequestDisassembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller.OrderControllerOpenApi
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.data.PageWrapper
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.data.PageableTranslator
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.CheckSecurity
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.Security
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.BusinessException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.EntityNotFoundException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.filter.OrderFilter
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Order
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.OrderStatus
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.User
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.OrderRepository
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.IssuanceOrderService
import br.edu.faeterj.petropolis.tcc.mealsdelivery.infrastructure.repository.spec.OrderSpecs
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.PagedModel
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(path = ["/v1/orders"])
class OrderController(

    @Autowired
    private val orderRepository: OrderRepository,

    @Autowired
    private val issuanceOrderService: IssuanceOrderService,

    @Autowired
    private val orderModelResponseAssembler: OrderModelResponseAssembler,

    @Autowired
    private val orderSummaryModelResponseAssembler: OrderSummaryModelResponseAssembler,

    @Autowired
    private val orderModelRequestDisassembler: OrderModelRequestDisassembler,

    @Autowired
    private val pagedResourcesAssembler: PagedResourcesAssembler<Order>,

    @Autowired
    private val security: Security

) : OrderControllerOpenApi {

    @CheckSecurity.Orders.CanSearch
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun search(
        filter: OrderFilter?,
        @PageableDefault(size = 10) pageable: Pageable
    ): PagedModel<OrderSummaryModelResponse> {
        val translatedPageable = translatePageable(pageable)

        var ordersPage: Page<Order> = orderRepository.findAll(OrderSpecs.usingFilter(filter), translatedPageable)
        ordersPage = PageWrapper(ordersPage, pageable)

        return pagedResourcesAssembler.toModel(ordersPage, orderSummaryModelResponseAssembler)
    }

    @CheckSecurity.Orders.CanFetch
    @GetMapping(path = ["/{orderCode}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun fetch(@PathVariable orderCode: String): OrderModelResponse {
        val order: Order = issuanceOrderService.fetchOrFail(orderCode)

        return orderModelResponseAssembler.toModel(order)
    }

    @CheckSecurity.Orders.CanCreate
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    override fun add(@RequestBody @Valid orderModelRequest: OrderModelRequest): OrderModelResponse {
        return try {
            var newOrder: Order = orderModelRequestDisassembler.toDomainObject(orderModelRequest)
            newOrder.customer = User()
            newOrder.customer.id = security.getUserId()
            newOrder.status = OrderStatus.CREATED
            newOrder = issuanceOrderService.issue(newOrder)

            orderModelResponseAssembler.toModel(newOrder)
        } catch (e: EntityNotFoundException) {
            throw BusinessException(e.message.toString(), e)
        }
    }

    private fun translatePageable(apiPageable: Pageable): Pageable {
        val mapping = mapOf(
            "code" to "code",
            "subtotal" to "subtotal",
            "restaurant.name" to "restaurant.name",
            "customerName" to "customer.name",
            "totalPrice" to "totalPrice",
        )

        return PageableTranslator.translate(apiPageable, mapping)
    }

}