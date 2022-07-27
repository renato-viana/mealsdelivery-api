package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.model

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.ProductModelResponse
import io.swagger.annotations.ApiModel
import org.springframework.hateoas.Links

@ApiModel("ProductsModelResponseOpenApi")
class ProductsModelResponseOpenApi(

    private val _embedded: ProductsEmbeddedModelResponseOpenApi? = null,

    private val _links: Links? = null

) {


    @ApiModel("ProductsEmbeddedModelResponse")
    companion object ProductsEmbeddedModelResponseOpenApi {

        private val products: List<ProductModelResponse>? = null

    }

}