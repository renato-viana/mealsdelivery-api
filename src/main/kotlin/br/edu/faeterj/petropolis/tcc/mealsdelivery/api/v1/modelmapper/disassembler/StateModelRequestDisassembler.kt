package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.disassembler

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.StateModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.State
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class StateModelRequestDisassembler(

    @Autowired
    private val modelMapper: ModelMapper? = null

) {

    fun toDomainObject(stateModelRequest: StateModelRequest?): State {
        return modelMapper!!.map(stateModelRequest, State::class.java)
    }

    fun copyToDomainObject(stateModelRequest: StateModelRequest, state: State) {
        modelMapper?.map(stateModelRequest, state)
    }

}