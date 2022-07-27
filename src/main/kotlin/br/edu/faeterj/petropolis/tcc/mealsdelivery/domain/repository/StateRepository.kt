package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.State
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StateRepository : JpaRepository<State, Long>