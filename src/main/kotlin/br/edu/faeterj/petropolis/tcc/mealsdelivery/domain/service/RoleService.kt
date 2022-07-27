package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.EntityInUseException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.RoleNotFoundException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Permission
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Role
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoleService(

    @Autowired
    private val roleRepository: RoleRepository,

    @Autowired
    private val permissionService: PermissionService,

    private val MSG_ROLE_IN_USE: String = "Role de código %d não pode ser removido, pois está em uso!"

) {

    @Transactional
    fun save(role: Role): Role = roleRepository.save(role)

    @Transactional
    fun delete(roleId: Long) {
        try {
            roleRepository.deleteById(roleId)
            roleRepository.flush()
        } catch (e: EmptyResultDataAccessException) {
            throw RoleNotFoundException(roleId)
        } catch (e: DataIntegrityViolationException) {
            throw EntityInUseException(String.format(MSG_ROLE_IN_USE, roleId))
        }
    }

    @Transactional
    fun disassociatePermission(roleId: Long?, permissionId: Long?) {
        val role: Role = fetchOrFail(roleId)
        val permission: Permission = permissionService.fetchOrFail(permissionId)
        role.removePermission(permission)
    }

    @Transactional
    fun associatePermission(roleId: Long?, permissionId: Long?) {
        val role: Role = fetchOrFail(roleId)
        val permission: Permission = permissionService.fetchOrFail(permissionId)
        role.addPermission(permission)
    }

    fun fetchOrFail(roleId: Long?): Role = roleRepository.findByIdOrNull(roleId) ?: throw RoleNotFoundException(roleId)

}