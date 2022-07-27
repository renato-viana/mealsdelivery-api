package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.CityNotFoundException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.EntityInUseException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.City
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.State
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.CityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CityService(

    @Autowired
    var cityRepository: CityRepository,

    @Autowired
    var stateService: StateService,

    private val MSG_CITY_IN_USE: String = "City de código %d não pode ser removida, pois está em uso!"

) {

    @Transactional
    fun save(city: City): City {
        val stateId: Long? = city.state?.id
        val state: State = stateService.fetchOrFail(stateId)
        city.state = state
        return cityRepository.save(city)
    }

    @Transactional
    fun delete(cityId: Long) {
        try {
            cityRepository.deleteById(cityId)
            cityRepository.flush()
        } catch (e: EmptyResultDataAccessException) {
            throw CityNotFoundException(cityId)
        } catch (e: DataIntegrityViolationException) {
            throw EntityInUseException(String.format(MSG_CITY_IN_USE, cityId))
        }
    }

    fun fetchOrFail(cityId: Long?): City = cityRepository.findByIdOrNull(cityId) ?: throw CityNotFoundException(cityId)

}