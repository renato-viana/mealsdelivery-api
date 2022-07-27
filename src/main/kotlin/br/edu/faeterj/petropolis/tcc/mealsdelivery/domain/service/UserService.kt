package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.BusinessException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.UserNotFoundException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Role
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.User
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(

    @Autowired
    private val userRepository: UserRepository,

    @Autowired
    private val roleService: RoleService,

    @Autowired
    private val passwordEncoder: PasswordEncoder

) {
    //TODO
    @Transactional
    fun save(user: User): User {
        userRepository.detach(user)

        val existingUser: User? = userRepository.findByEmail(user.email)

        if (existingUser != null && existingUser != user) {
            throw BusinessException("Já existe um usuário cadastrado com o e-mail ${user.email}")
        }

        if (user.isNew()) {
            user.password = passwordEncoder.encode(user.password)
        }

        return userRepository.save(user)
    }

    @Transactional
    fun changePassword(userId: Long, password: String, newPassword: String) {
        val user: User = fetchOrFail(userId)

        if (!passwordEncoder.matches(password, user.password)) {
            throw BusinessException("Senha informada não coincide com a senha do usuário.")
        }

        user.password = passwordEncoder.encode(newPassword)
    }

    @Transactional
    fun disassociateRole(userId: Long?, roleId: Long?) {
        val user: User = fetchOrFail(userId)
        val role: Role = roleService.fetchOrFail(roleId)

        user.removeRole(role)
    }

    @Transactional
    fun associateRole(userId: Long?, roleId: Long?) {
        val user: User = fetchOrFail(userId)
        val role: Role = roleService.fetchOrFail(roleId)

        user.addRole(role)
    }

    fun fetchOrFail(userId: Long?): User = userRepository.findByIdOrNull(userId) ?: throw UserNotFoundException(userId)

}