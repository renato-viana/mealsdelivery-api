package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.CuisineNotFoundException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.EntityInUseException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Cuisine
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.CuisineRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CuisineService(

    @Autowired
    var cuisineRepository: CuisineRepository,

    private val MSG_CUISINE_IN_USE: String = "Cuisine de código %d não pode ser removida, pois está em uso!"

) {

    @Transactional
    fun save(cuisine: Cuisine) = cuisineRepository.save(cuisine)

    @Transactional
    fun delete(cuisineId: Long) {
        try {
            cuisineRepository.deleteById(cuisineId)
            cuisineRepository.flush()
        } catch (e: EmptyResultDataAccessException) {
            throw CuisineNotFoundException(cuisineId)
        } catch (e: DataIntegrityViolationException) {
            throw EntityInUseException(String.format(MSG_CUISINE_IN_USE, cuisineId))
        }
    }

    fun fetchOrFail(cuisineId: Long): Cuisine {
        return cuisineRepository.findById(cuisineId)
            .orElseThrow { CuisineNotFoundException(cuisineId) }
    }

}