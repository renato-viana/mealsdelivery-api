package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.helper.ResourceLinkHelper
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.ProductModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.ProductModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler.ProductModelResponseAssembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.disassembler.ProductModelRequestDisassembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller.ProductControllerOpenApi
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.CheckSecurity
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Product
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Restaurant
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.ProductRepository
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.ProductService
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.RestaurantService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(path = ["/v1/restaurants/{restaurantId}/products"])
class ProductController(

    @Autowired
    private val productRepository: ProductRepository,

    @Autowired
    private val productService: ProductService,

    @Autowired
    private val restaurantService: RestaurantService,

    @Autowired
    private val productModelResponseAssembler: ProductModelResponseAssembler,

    @Autowired
    private val productModelRequestDisassembler: ProductModelRequestDisassembler,

    @Autowired
    private val resourceLinkHelper: ResourceLinkHelper

) : ProductControllerOpenApi {

    @CheckSecurity.Restaurants.CanConsult
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun list(
        @PathVariable restaurantId: Long?,
        @RequestParam(required = false, defaultValue = "false") includeInactive: Boolean?
    ): CollectionModel<ProductModelResponse> {
        val restaurant: Restaurant = restaurantService.fetchOrFail(restaurantId)

        val products: List<Product?> = if (includeInactive == true) productRepository.findByRestaurant(restaurant) else
            productRepository.findActivesByRestaurant(restaurant)

        return productModelResponseAssembler.toCollectionModel(products)
            .add(resourceLinkHelper.linkToProducts(restaurantId))
    }

    @CheckSecurity.Restaurants.CanConsult
    @GetMapping(path = ["/{productId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun fetch(@PathVariable restaurantId: Long?, @PathVariable productId: Long?): ProductModelResponse {
        val product: Product = productService.fetchOrFail(restaurantId, productId)
        return productModelResponseAssembler.toModel(product)
    }

    @CheckSecurity.Restaurants.CanManageOperation
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    override fun add(
        @PathVariable restaurantId: Long,
        @RequestBody @Valid productModelRequest: ProductModelRequest
    ): ProductModelResponse {
        val restaurant: Restaurant = restaurantService.fetchOrFail(restaurantId)

        var product: Product = productModelRequestDisassembler.toDomainObject(productModelRequest)

        product.restaurant = restaurant
        product = productService.save(product)

        return productModelResponseAssembler.toModel(product)
    }

    @CheckSecurity.Restaurants.CanManageOperation
    @PutMapping(path = ["/{productId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun update(
        @PathVariable restaurantId: Long, @PathVariable productId: Long,
        @RequestBody @Valid productModelRequest: ProductModelRequest
    ): ProductModelResponse {
        var currentProduct: Product = productService.fetchOrFail(restaurantId, productId)

        productModelRequestDisassembler.copyToDomainObject(productModelRequest, currentProduct)

        currentProduct = productService.save(currentProduct)

        return productModelResponseAssembler.toModel(currentProduct)
    }

}