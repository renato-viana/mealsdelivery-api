package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Cuisine
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CuisineRepository : CustomJpaRepository<Cuisine, Long> {

    fun findAllByNameContaining(name: String): List<Cuisine?>

    fun findByName(name: String): Optional<Cuisine?>

    fun existsByName(name: String): Boolean

}