package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.EntityInUseException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.StateNotFoundException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.State
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.StateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StateService(

    @Autowired
    var stateRepository: StateRepository,

    private val MSG_STATE_IN_USE: String = "State de código %d não pode ser removido, pois está em uso!"

) {

    @Transactional
    fun save(state: State): State {
        return stateRepository.save(state)
    }

    @Transactional
    fun delete(stateId: Long) {
        try {
            stateRepository.deleteById(stateId)
            stateRepository.flush()
        } catch (e: EmptyResultDataAccessException) {
            throw StateNotFoundException(stateId)
        } catch (e: DataIntegrityViolationException) {
            throw EntityInUseException(String.format(MSG_STATE_IN_USE, stateId))
        }
    }

    fun fetchOrFail(stateId: Long?): State =
        stateRepository.findByIdOrNull(stateId) ?: throw StateNotFoundException(stateId)

}