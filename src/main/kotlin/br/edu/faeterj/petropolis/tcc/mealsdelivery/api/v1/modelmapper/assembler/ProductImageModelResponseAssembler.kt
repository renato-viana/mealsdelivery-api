package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller.ProductController
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.helper.ResourceLinkHelper
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.ProductImageModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.Security
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.ProductImage
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.stereotype.Component

@Component
class ProductImageModelResponseAssembler(

    @Autowired
    private val modelMapper: ModelMapper,

    @Autowired
    private val resourceLinkHelper: ResourceLinkHelper,

    @Autowired
    private val security: Security

) : RepresentationModelAssemblerSupport<ProductImage, ProductImageModelResponse>(
    ProductController::class.java, ProductImageModelResponse::class.java
) {

    override fun toModel(productImage: ProductImage): ProductImageModelResponse {
        val productImageModelResponse: ProductImageModelResponse =
            modelMapper.map(productImage, ProductImageModelResponse::class.java)

        // Quem pode consultar restaurantes, também pode consultar os produtos e as imagens
        if (security.canConsultRestaurants()) {
            productImageModelResponse.add(
                resourceLinkHelper.linkToProductImage(productImage.getRestaurantId(), productImage.product?.id)
            )

            productImageModelResponse.add(
                resourceLinkHelper.linkToProduct(
                    productImage.getRestaurantId(), productImage.product?.id, "products"
                )
            )
        }

        return productImageModelResponse
    }
}