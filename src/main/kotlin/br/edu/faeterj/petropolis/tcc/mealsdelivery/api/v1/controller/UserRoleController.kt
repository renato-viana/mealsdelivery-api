package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.helper.ResourceLinkHelper
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.RoleModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler.RoleModelResponseAssembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller.UserRoleControllerOpenApi
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.CheckSecurity
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.Security
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.User
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.function.Consumer

@RestController
@RequestMapping(path = ["/v1/users/{userId}/roles"])
class UserRoleController(

    @Autowired
    private val userService: UserService,

    @Autowired
    private val roleModelResponseAssembler: RoleModelResponseAssembler,

    @Autowired
    private val resourceLinkHelper: ResourceLinkHelper,

    @Autowired
    private val security: Security

) : UserRoleControllerOpenApi {

    @CheckSecurity.UsersRolesPermissions.CanConsult
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun list(@PathVariable userId: Long?): CollectionModel<RoleModelResponse> {
        val user: User = userService.fetchOrFail(userId)

        val roleModelResponse: CollectionModel<RoleModelResponse> =
            roleModelResponseAssembler.toCollectionModel(user.roles)
                .removeLinks()

        if (security.canEditUsersRolesPermissions()) {
            roleModelResponse.add(resourceLinkHelper.linkToUserRoleAssociation(userId, "associate"))
            roleModelResponse.content.forEach(Consumer { roleModel: RoleModelResponse ->
                roleModel.add(
                    resourceLinkHelper.linkToUserRoleDisassociation(
                        userId, roleModel.id, "disassociate"
                    )
                )
            })
        }

        return roleModelResponse
    }

    @CheckSecurity.UsersRolesPermissions.CanEdit
    @DeleteMapping("/{roleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun disassociate(@PathVariable userId: Long?, @PathVariable roleId: Long?): ResponseEntity<Void> {
        userService.disassociateRole(userId, roleId)

        return ResponseEntity.noContent().build()
    }

    @CheckSecurity.UsersRolesPermissions.CanEdit
    @PutMapping("/{roleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun associate(@PathVariable userId: Long?, @PathVariable roleId: Long?): ResponseEntity<Void> {
        userService.associateRole(userId, roleId)

        return ResponseEntity.noContent().build()
    }

}