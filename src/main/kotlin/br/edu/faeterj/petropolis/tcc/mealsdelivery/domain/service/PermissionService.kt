package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.exception.PermissionNotFoundException
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Permission
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.PermissionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PermissionService(

    @Autowired
    private val permissionRepository: PermissionRepository

) {

    fun fetchOrFail(permissionId: Long?): Permission =
        permissionRepository.findByIdOrNull(permissionId) ?: throw PermissionNotFoundException(permissionId)

}