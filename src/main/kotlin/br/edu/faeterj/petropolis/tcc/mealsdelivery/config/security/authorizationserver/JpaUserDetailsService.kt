package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.authorizationserver

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.User
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.stream.Collectors

@Service
class JpaUserDetailsService(

    @Autowired
    var userRepository: UserRepository

) : UserDetailsService {

    @Transactional(readOnly = true)
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(userName: String): UserDetails {
        val user: User = userRepository.findByEmail(userName)
            ?: throw UsernameNotFoundException("Usuário não encontrado com o e-mail informado")

        return AuthUser(user, getAuthorities(user))
    }

    private fun getAuthorities(user: User): Collection<GrantedAuthority> {
        return user.roles.stream()
            .flatMap { role -> role.permissions.stream() }
            .map { permission -> SimpleGrantedAuthority(permission.name.uppercase(Locale.getDefault())) }
            .collect(Collectors.toSet())
    }

}