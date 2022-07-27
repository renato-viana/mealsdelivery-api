package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.helper.ResourceUriHelper
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.CityModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.CityModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler.CityModelResponseAssembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.disassembler.CityModelRequestDisassembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller.CityControllerOpenApi
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.CheckSecurity
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.BusinessException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.StateNotFoundException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.City
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.CityRepository
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.CityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(path = ["/v1/cities"])
class CityController(

    @Autowired
    private val cityRepository: CityRepository,

    @Autowired
    private val cityService: CityService,

    @Autowired
    private val cityModelResponseAssembler: CityModelResponseAssembler,

    @Autowired
    private val cityModelRequestDisassembler: CityModelRequestDisassembler

) : CityControllerOpenApi {

    @CheckSecurity.Cities.CanConsult
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun list(): CollectionModel<CityModelResponse> {
        val cities: List<City> = cityRepository.findAll()

        return cityModelResponseAssembler.toCollectionModel(cities)
    }

    @CheckSecurity.Cities.CanConsult
    @GetMapping(path = ["/{cityId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun fetch(@PathVariable cityId: Long?): CityModelResponse {
        val city: City = cityService.fetchOrFail(cityId)

        return cityModelResponseAssembler.toModel(city)
    }

    @CheckSecurity.Cities.CanEdit
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    override fun add(@RequestBody @Valid cityModelRequest: CityModelRequest): CityModelResponse {
        return try {
            var city: City = cityModelRequestDisassembler.toDomainObject(cityModelRequest)
            city = cityService.save(city)

            val cityModelResponse: CityModelResponse = cityModelResponseAssembler.toModel(city)

            ResourceUriHelper.addUriInResponseHeader(cityModelResponse.id)

            cityModelResponse
        } catch (e: StateNotFoundException) {
            throw BusinessException(e.message!!, e)
        }
    }

    @CheckSecurity.Cities.CanEdit
    @PutMapping(path = ["/{cityId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun update(
        @PathVariable cityId: Long,
        @RequestBody @Valid cityModelRequest: CityModelRequest
    ): CityModelResponse {
        return try {
            var currentCity: City = cityService.fetchOrFail(cityId)
            cityModelRequestDisassembler.copyToDomainObject(cityModelRequest, currentCity)
            currentCity = cityService.save(currentCity)
            cityModelResponseAssembler.toModel(currentCity)
        } catch (e: StateNotFoundException) {
            throw BusinessException(e.message!!, e)
        }
    }

    @CheckSecurity.Cities.CanEdit
    @DeleteMapping("/{cityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun remove(@PathVariable cityId: Long) {
        cityService.delete(cityId)
    }

}