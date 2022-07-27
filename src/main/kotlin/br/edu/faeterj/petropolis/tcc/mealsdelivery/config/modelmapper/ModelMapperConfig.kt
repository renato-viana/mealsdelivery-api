package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.modelmapper

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.AddressModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Address
import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ModelMapperConfig {

    @Bean
    fun modelMapper(): ModelMapper {
        val modelMapper = ModelMapper()

        val addressToAddressOutputDTOTypeMap = modelMapper.createTypeMap(
            Address::class.java, AddressModelResponse::class.java
        )

        addressToAddressOutputDTOTypeMap.addMapping({ it.city?.state?.name },
            { dest: AddressModelResponse, value: String? -> dest.city?.state = value.toString() }
        )

        return modelMapper
    }

}