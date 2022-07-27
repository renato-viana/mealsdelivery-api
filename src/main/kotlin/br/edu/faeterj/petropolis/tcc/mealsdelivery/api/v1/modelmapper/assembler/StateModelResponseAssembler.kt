package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller.StateController
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.helper.ResourceLinkHelper
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.StateModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.Security
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.State
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.stereotype.Component

@Component
class StateModelResponseAssembler(

    @Autowired
    private val modelMapper: ModelMapper,

    @Autowired
    private val resourceLinkHelper: ResourceLinkHelper,

    @Autowired
    private val security: Security

) : RepresentationModelAssemblerSupport<State, StateModelResponse>(
    StateController::class.java, StateModelResponse::class.java
) {

    override fun toModel(state: State): StateModelResponse {
        val stateModelResponse: StateModelResponse = createModelWithId(state.id ?: -1L, state)

        modelMapper.map(state, stateModelResponse)

        if (security.canConsultStates()) {
            stateModelResponse.add(resourceLinkHelper.linkToStates("states"))
        }

        return stateModelResponse
    }

    override fun toCollectionModel(entities: Iterable<State?>): CollectionModel<StateModelResponse> {
        val collectionModelResponse: CollectionModel<StateModelResponse> = super.toCollectionModel(entities)

        if (security.canConsultStates()) {
            collectionModelResponse.add(resourceLinkHelper.linkToStates())
        }

        return collectionModelResponse
    }

}