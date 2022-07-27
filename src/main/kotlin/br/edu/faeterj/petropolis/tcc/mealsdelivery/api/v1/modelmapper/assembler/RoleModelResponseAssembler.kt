package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller.RoleController
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.helper.ResourceLinkHelper
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.RoleModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.Security
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Role
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.stereotype.Component

@Component
class RoleModelResponseAssembler(

    @Autowired
    private val modelMapper: ModelMapper,

    @Autowired
    private val resourceLinkHelper: ResourceLinkHelper,

    @Autowired
    private val security: Security

) : RepresentationModelAssemblerSupport<Role, RoleModelResponse>(
    RoleController::class.java, RoleModelResponse::class.java
) {

    override fun toModel(role: Role): RoleModelResponse {
        val roleModelResponse: RoleModelResponse = createModelWithId(role.id ?: -1L, role)

        modelMapper.map(role, roleModelResponse)

        if (security.canConsultUsersRolesPermissions()) {
            roleModelResponse.add(resourceLinkHelper.linkToRoles("roles"))
            roleModelResponse.add(resourceLinkHelper.linkToRolePermissions(role.id, "permissions"))
        }

        return roleModelResponse
    }

    override fun toCollectionModel(entities: Iterable<Role?>): CollectionModel<RoleModelResponse> {
        val collectionModelResponse: CollectionModel<RoleModelResponse> = super.toCollectionModel(entities)
        if (security.canConsultUsersRolesPermissions()) {
            collectionModelResponse.add(resourceLinkHelper.linkToRoles())
        }
        return collectionModelResponse
    }

}