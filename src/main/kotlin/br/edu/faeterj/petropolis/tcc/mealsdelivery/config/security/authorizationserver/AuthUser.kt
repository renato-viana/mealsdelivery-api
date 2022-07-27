package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.authorizationserver

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.User
import org.springframework.security.core.GrantedAuthority


class AuthUser(user: User, authorities: Collection<GrantedAuthority>) :
    org.springframework.security.core.userdetails.User(user.email, user.password, authorities) {

    val userId: Long?
    val fullName: String?

    init {
        userId = user.id
        fullName = user.name
    }

}