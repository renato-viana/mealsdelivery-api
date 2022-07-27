package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller.ProductController
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.helper.ResourceLinkHelper
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.ProductModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.Security
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Product
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.stereotype.Component

@Component
class ProductModelResponseAssembler(

    @Autowired
    private val modelMapper: ModelMapper,

    @Autowired
    private val resourceLinkHelper: ResourceLinkHelper,

    @Autowired
    private val security: Security

) : RepresentationModelAssemblerSupport<Product, ProductModelResponse>(
    ProductController::class.java, ProductModelResponse::class.java
) {

    override fun toModel(product: Product): ProductModelResponse {
        val productModelResponse: ProductModelResponse =
            createModelWithId(product.id ?: -1L, product, product.restaurant.id)

        modelMapper.map(product, productModelResponse)

        if (security.canConsultRestaurants()) {
            productModelResponse.add(resourceLinkHelper.linkToProducts(product.restaurant.id, "products"))

            productModelResponse.add(resourceLinkHelper.linkToProductImage(product.restaurant.id, product.id, "image"))
        }

        return productModelResponse
    }

}