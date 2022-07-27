package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.disassembler

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.CuisineModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Cuisine
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CuisineModelRequestDisassembler(

    @Autowired
    var modelMapper: ModelMapper

) {

    fun toDomainObject(cuisineModelRequest: CuisineModelRequest?): Cuisine {
        return modelMapper.map(cuisineModelRequest, Cuisine::class.java)
    }

    fun copyToDomainObject(cuisineModelRequest: CuisineModelRequest, cuisine: Cuisine) {
        modelMapper.map(cuisineModelRequest, cuisine)
    }

}