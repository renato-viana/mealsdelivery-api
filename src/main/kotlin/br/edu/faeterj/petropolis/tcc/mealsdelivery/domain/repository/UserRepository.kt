package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.User
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : CustomJpaRepository<User, Long> {

    fun findByEmail(email: String?): User?

}