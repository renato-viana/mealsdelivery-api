package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.City
import org.springframework.stereotype.Repository

@Repository
interface CityRepository : CustomJpaRepository<City, Long?>