package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.disassembler

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.ProductModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Product
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ProductModelRequestDisassembler(

    @Autowired
    private val modelMapper: ModelMapper? = null

) {

    fun toDomainObject(productModelRequest: ProductModelRequest): Product {
        return modelMapper!!.map(productModelRequest, Product::class.java)
    }

    fun copyToDomainObject(productModelRequest: ProductModelRequest, product: Product) {
        modelMapper?.map(productModelRequest, product)
    }

}