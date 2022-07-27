package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.helper.ResourceLinkHelper
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.PermissionModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler.PermissionModelResponseAssembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller.RolePermissionControllerOpenApi
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.CheckSecurity
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.Security
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Role
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.RoleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.function.Consumer

@RestController
@RequestMapping(path = ["/v1/roles/{roleId}/permissions"])
class RolePermissionController(

    @Autowired
    private val roleService: RoleService,

    @Autowired
    private val permissionModelResponseAssembler: PermissionModelResponseAssembler,

    @Autowired
    private val resourceLinkHelper: ResourceLinkHelper,

    @Autowired
    private val security: Security

) : RolePermissionControllerOpenApi {

    @CheckSecurity.UsersRolesPermissions.CanConsult
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun list(@PathVariable roleId: Long?): CollectionModel<PermissionModelResponse> {
        val role: Role = roleService.fetchOrFail(roleId)

        val permissionsModelResponse: CollectionModel<PermissionModelResponse> =
            permissionModelResponseAssembler.toCollectionModel(role.permissions)
                .removeLinks()

        permissionsModelResponse.add(resourceLinkHelper.linkToRolePermissions(roleId))

        if (security.canEditUsersRolesPermissions()) {
            permissionsModelResponse.add(resourceLinkHelper.linkToRolePermissionAssociation(roleId, "associate"))
            permissionsModelResponse.content.forEach(
                Consumer { permissionModel: PermissionModelResponse ->
                    permissionModel.add(
                        resourceLinkHelper.linkToRolePermissionDisassociation(
                            roleId, permissionModel.id, "disassociate"
                        )
                    )
                }
            )
        }

        return permissionsModelResponse
    }

    @CheckSecurity.UsersRolesPermissions.CanEdit
    @DeleteMapping(path = ["/{permissionId}"])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun disassociate(@PathVariable roleId: Long?, @PathVariable permissionId: Long?): ResponseEntity<Void> {
        roleService.disassociatePermission(roleId, permissionId)

        return ResponseEntity.noContent().build()
    }

    @CheckSecurity.UsersRolesPermissions.CanEdit
    @PutMapping(path = ["/{permissionId}"])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun associate(@PathVariable roleId: Long?, @PathVariable permissionId: Long?): ResponseEntity<Void> {
        roleService.associatePermission(roleId, permissionId)

        return ResponseEntity.noContent().build()
    }

}