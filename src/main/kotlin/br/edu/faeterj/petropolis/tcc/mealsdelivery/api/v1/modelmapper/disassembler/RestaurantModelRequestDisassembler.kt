package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.disassembler

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.RestaurantModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.City
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Cuisine
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Restaurant
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RestaurantModelRequestDisassembler(

    @Autowired
    private val modelMapper: ModelMapper

) {

    fun toDomainObject(restaurantModelRequest: RestaurantModelRequest): Restaurant {
        return modelMapper.map(restaurantModelRequest, Restaurant::class.java)
    }

    fun copyToDomainObject(restaurantModelRequest: RestaurantModelRequest, restaurant: Restaurant) {
        restaurant.cuisine = Cuisine()
        restaurant.address.city = City()

        modelMapper.map(restaurantModelRequest, restaurant)
    }

}