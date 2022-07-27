package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.CuisineModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.CuisineModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler.CuisineModelResponseAssembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.disassembler.CuisineModelRequestDisassembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller.CuisineControllerOpenApi
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.CheckSecurity
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Cuisine
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.CuisineRepository
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.CuisineService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.PagedModel
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(path = ["/v1/cuisines"])
class CuisineController(

    @Autowired
    private val cuisineRepository: CuisineRepository,

    @Autowired
    private val cuisineService: CuisineService,

    @Autowired
    private val cuisineModelResponseAssembler: CuisineModelResponseAssembler,

    @Autowired
    private val cuisineModelRequestDisassembler: CuisineModelRequestDisassembler,

    @Autowired
    private val pagedResourcesAssembler: PagedResourcesAssembler<Cuisine>

) : CuisineControllerOpenApi {

    @CheckSecurity.Cuisines.CanConsult
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun list(@PageableDefault(size = 10) pageable: Pageable): PagedModel<CuisineModelResponse> {
        val cuisinesPage: Page<Cuisine> = cuisineRepository.findAll(pageable)

        return pagedResourcesAssembler.toModel(cuisinesPage, cuisineModelResponseAssembler)
    }

    @CheckSecurity.Cuisines.CanConsult
    @GetMapping(path = ["/{cuisineId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun fetch(@PathVariable cuisineId: Long): CuisineModelResponse {
        val cuisine: Cuisine = cuisineService.fetchOrFail(cuisineId)

        return cuisineModelResponseAssembler.toModel(cuisine)
    }

    @CheckSecurity.Cuisines.CanEdit
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    override fun add(@RequestBody @Valid cuisineModelRequest: CuisineModelRequest): CuisineModelResponse {
        var cuisine: Cuisine = cuisineModelRequestDisassembler.toDomainObject(cuisineModelRequest)
        cuisine = cuisineService.save(cuisine)

        return cuisineModelResponseAssembler.toModel(cuisine)
    }

    @CheckSecurity.Cuisines.CanEdit
    @PutMapping(path = ["/{cuisineId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun update(
        @PathVariable cuisineId: Long,
        @RequestBody @Valid cuisineModelRequest: CuisineModelRequest
    ): CuisineModelResponse {
        var currentCuisine: Cuisine = cuisineService.fetchOrFail(cuisineId)
        cuisineModelRequestDisassembler.copyToDomainObject(cuisineModelRequest, currentCuisine)
        currentCuisine = cuisineService.save(currentCuisine)

        return cuisineModelResponseAssembler.toModel(currentCuisine)
    }

    @CheckSecurity.Cuisines.CanEdit
    @DeleteMapping("/{cuisineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun remove(@PathVariable cuisineId: Long) {
        cuisineService.delete(cuisineId)
    }

}