package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.RestaurantModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.RestaurantModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.RestaurantNameOnlyModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.RestaurantSummaryModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler.RestaurantModelResponseAssembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler.RestaurantNameOnlyModelResponseAssembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler.RestaurantSummaryModelResponseAssembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.disassembler.RestaurantModelRequestDisassembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller.RestaurantControllerOpenApi
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.CheckSecurity
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.BusinessException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.CityNotFoundException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.CuisineNotFoundException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.RestaurantNotFoundException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Restaurant
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.RestaurantRepository
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.RestaurantService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(path = ["/v1/restaurants"])
class RestaurantController(

    @Autowired
    private val restaurantRepository: RestaurantRepository,

    @Autowired
    private val restaurantService: RestaurantService,

    @Autowired
    private val restaurantModelResponseAssembler: RestaurantModelResponseAssembler,

    @Autowired
    private val restaurantModelRequestDisassembler: RestaurantModelRequestDisassembler,

    @Autowired
    private val restaurantSummaryModelResponseAssembler: RestaurantSummaryModelResponseAssembler,

    @Autowired
    private val restaurantNameOnlyModelResponseAssembler: RestaurantNameOnlyModelResponseAssembler

) : RestaurantControllerOpenApi {

    @CheckSecurity.Restaurants.CanConsult
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun list(): CollectionModel<RestaurantSummaryModelResponse> {
        return restaurantSummaryModelResponseAssembler
            .toCollectionModel(restaurantRepository.findAll())
    }

    @CheckSecurity.Restaurants.CanConsult
    @GetMapping(params = ["projection=just-name"], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun listNamesOnly(): CollectionModel<RestaurantNameOnlyModelResponse> {
        return restaurantNameOnlyModelResponseAssembler.toCollectionModel(restaurantRepository.findAll())
    }

    @CheckSecurity.Restaurants.CanConsult
    @GetMapping(path = ["/{restaurantId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun fetch(@PathVariable restaurantId: Long?): RestaurantModelResponse {
        val restaurant: Restaurant = restaurantService.fetchOrFail(restaurantId)

        return restaurantModelResponseAssembler.toModel(restaurant)
    }

    @CheckSecurity.Restaurants.CanManageRegistration
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    override fun add(@RequestBody @Valid restaurantModelRequest: RestaurantModelRequest): RestaurantModelResponse {
        return try {
            var restaurant: Restaurant = restaurantModelRequestDisassembler.toDomainObject(restaurantModelRequest)
            restaurant = restaurantService.save(restaurant)

            restaurantModelResponseAssembler.toModel(restaurant)
        } catch (e: CuisineNotFoundException) {
            throw BusinessException(e.message!!)
        } catch (e: CityNotFoundException) {
            throw BusinessException(e.message!!)
        }
    }

    @CheckSecurity.Restaurants.CanManageRegistration
    @PutMapping(path = ["/{restaurantId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun update(
        @PathVariable restaurantId: Long,
        @RequestBody @Valid restaurantModelRequest: RestaurantModelRequest
    ): RestaurantModelResponse {
        return try {
            var currentRestaurant: Restaurant = restaurantService.fetchOrFail(restaurantId)

            restaurantModelRequestDisassembler.copyToDomainObject(restaurantModelRequest, currentRestaurant)

            currentRestaurant = restaurantService.save(currentRestaurant)

            restaurantModelResponseAssembler.toModel(currentRestaurant)
        } catch (e: CuisineNotFoundException) {
            throw BusinessException(e.message!!)
        } catch (e: CityNotFoundException) {
            throw BusinessException(e.message!!)
        }
    }

    @PutMapping("/{restaurantId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun activate(@PathVariable restaurantId: Long?): ResponseEntity<Void> {
        restaurantService.activate(restaurantId)

        return ResponseEntity.noContent().build()
    }

    @CheckSecurity.Restaurants.CanManageRegistration
    @PutMapping("/activations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun activateMany(@RequestBody restaurantsId: List<Long>) {
        try {
            restaurantService.activate(restaurantsId)
        } catch (e: RestaurantNotFoundException) {
            throw BusinessException(e.message)
        }
    }

    @CheckSecurity.Restaurants.CanManageRegistration
    @DeleteMapping("/{restaurantId}/inactive")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun inactivate(@PathVariable restaurantId: Long?): ResponseEntity<Void> {
        restaurantService.inactivate(restaurantId)

        return ResponseEntity.noContent().build()
    }

    @CheckSecurity.Restaurants.CanManageRegistration
    @DeleteMapping("/inactivations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun inactivateMany(@RequestBody restaurantsId: List<Long>) {
        try {
            restaurantService.inactivate(restaurantsId)
        } catch (e: RestaurantNotFoundException) {
            throw BusinessException(e.message, e)
        }
    }

    @CheckSecurity.Restaurants.CanManageOperation
    @PutMapping("/{restaurantId}/opening")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun open(@PathVariable restaurantId: Long?): ResponseEntity<Void> {
        restaurantService.open(restaurantId)

        return ResponseEntity.noContent().build()
    }

    @CheckSecurity.Restaurants.CanManageOperation
    @PutMapping("/{restaurantId}/closure")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun close(@PathVariable restaurantId: Long?): ResponseEntity<Void> {
        restaurantService.close(restaurantId)

        return ResponseEntity.noContent().build()
    }

}