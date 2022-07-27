package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.StateModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.StateModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler.StateModelResponseAssembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.disassembler.StateModelRequestDisassembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller.StateControllerOpenApi
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.CheckSecurity
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.State
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.StateRepository
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.StateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(path = ["/v1/states"])
class StateController(

    @Autowired
    private val stateRepository: StateRepository,

    @Autowired
    private val stateService: StateService,

    @Autowired
    private val stateModelResponseAssembler: StateModelResponseAssembler,

    @Autowired
    private val stateModelRequestDisassembler: StateModelRequestDisassembler

) : StateControllerOpenApi {

    @CheckSecurity.States.CanConsult
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun list(): CollectionModel<StateModelResponse> {
        val states: List<State> = stateRepository.findAll()

        return stateModelResponseAssembler.toCollectionModel(states)
    }

    @CheckSecurity.States.CanConsult
    @GetMapping(path = ["/{stateId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun fetch(@PathVariable stateId: Long?): StateModelResponse {
        val state: State = stateService.fetchOrFail(stateId)

        return stateModelResponseAssembler.toModel(state)
    }

    @CheckSecurity.States.CanEdit
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    override fun add(@RequestBody @Valid stateModelRequest: StateModelRequest): StateModelResponse {
        var state: State = stateModelRequestDisassembler.toDomainObject(stateModelRequest)
        state = stateService.save(state)

        return stateModelResponseAssembler.toModel(state)
    }

    @CheckSecurity.States.CanEdit
    @PutMapping(path = ["/{stateId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun update(
        @PathVariable stateId: Long,
        @RequestBody @Valid stateModelRequest: StateModelRequest
    ): StateModelResponse {
        var currentState: State = stateService.fetchOrFail(stateId)

        stateModelRequestDisassembler.copyToDomainObject(stateModelRequest, currentState)

        currentState = stateService.save(currentState)

        return stateModelResponseAssembler.toModel(currentState)
    }

    @CheckSecurity.States.CanEdit
    @DeleteMapping("/{stateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun remove(@PathVariable stateId: Long) {
        stateService.delete(stateId)
    }

}