package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.PermissionModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler.PermissionModelResponseAssembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller.PermissionControllerOpenApi
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.CheckSecurity
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Permission
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.PermissionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/v1/permissions"], produces = [MediaType.APPLICATION_JSON_VALUE])
class PermissionController(

    @Autowired
    private val permissionRepository: PermissionRepository,

    @Autowired
    private val permissionModelResponseAssembler: PermissionModelResponseAssembler,

    ) : PermissionControllerOpenApi {

    @CheckSecurity.UsersRolesPermissions.CanConsult
    @GetMapping
    override fun list(): CollectionModel<PermissionModelResponse> {
        val permissions: List<Permission> = permissionRepository.findAll()

        return permissionModelResponseAssembler.toCollectionModel(permissions)
    }

}