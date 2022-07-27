package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.disassembler

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.CityModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.City
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.State
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CityModelRequestDisassembler(

    @Autowired
    private val modelMapper: ModelMapper

) {

    fun toDomainObject(cityModelRequest: CityModelRequest): City {
        return modelMapper.map(cityModelRequest, City::class.java)
    }

    fun copyToDomainObject(cityModelRequest: CityModelRequest, city: City) {
        city.state = State()
        modelMapper.map(cityModelRequest, city)
    }

}