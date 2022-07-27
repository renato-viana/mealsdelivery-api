package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller.CityController
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.helper.ResourceLinkHelper
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.CityModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.Security
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.City
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.stereotype.Component

@Component
class CityModelResponseAssembler(

    @Autowired
    private val modelMapper: ModelMapper,

    @Autowired
    private val resourceLinkHelper: ResourceLinkHelper,

    @Autowired
    private val security: Security

) : RepresentationModelAssemblerSupport<City, CityModelResponse>(
    CityController::class.java, CityModelResponse::class.java
) {

    override fun toModel(city: City): CityModelResponse {
        val cityModelResponse: CityModelResponse = createModelWithId(city.id ?: -1L, city)

        modelMapper.map(city, cityModelResponse)

        if (security.canConsultCities()) {
            cityModelResponse.add(resourceLinkHelper.linkToCities("cities"))
        }

        if (security.canConsultStates()) {
            cityModelResponse.state?.add(resourceLinkHelper.linkToState(cityModelResponse.state?.id))
        }

        return cityModelResponse
    }

    override fun toCollectionModel(entities: Iterable<City?>): CollectionModel<CityModelResponse> {
        val collectionModelResponse: CollectionModel<CityModelResponse> = super.toCollectionModel(entities)

        if (security.canConsultCities()) {
            collectionModelResponse.add(resourceLinkHelper.linkToCities())
        }

        return collectionModelResponse
    }

}