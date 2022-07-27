package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller.CuisineController
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.helper.ResourceLinkHelper
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.CuisineModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.Security
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Cuisine
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.stereotype.Component

@Component
class CuisineModelResponseAssembler(

    @Autowired
    var modelMapper: ModelMapper,

    @Autowired
    var resourceLinkHelper: ResourceLinkHelper,

    @Autowired
    var security: Security

) : RepresentationModelAssemblerSupport<Cuisine, CuisineModelResponse>(
    CuisineController::class.java, CuisineModelResponse::class.java
) {

    override fun toModel(cuisine: Cuisine): CuisineModelResponse {
        val cuisineModelResponse: CuisineModelResponse = createModelWithId(cuisine.id!!, cuisine)

        modelMapper.map(cuisine, cuisineModelResponse)

        if (security.canConsultCuisines()) {
            cuisineModelResponse.add(resourceLinkHelper.linkToCuisines("cuisines"))
        }

        return cuisineModelResponse
    }

}