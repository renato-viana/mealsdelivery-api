package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.helper.ResourceLinkHelper
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.PermissionModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.Security
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Permission
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.stereotype.Component

@Component
class PermissionModelResponseAssembler(

    @Autowired
    private val modelMapper: ModelMapper? = null,

    @Autowired
    private val resourceLinkHelper: ResourceLinkHelper? = null,

    @Autowired
    private val security: Security

) : RepresentationModelAssembler<Permission, PermissionModelResponse> {

    override fun toModel(permission: Permission): PermissionModelResponse {
        return modelMapper?.map(permission, PermissionModelResponse::class.java)!!
    }

    override fun toCollectionModel(entities: Iterable<Permission?>): CollectionModel<PermissionModelResponse> {
        val collectionModelResponse: CollectionModel<PermissionModelResponse> = super.toCollectionModel(entities)

        if (security.canConsultUsersRolesPermissions()) {
            collectionModelResponse.add(resourceLinkHelper?.linkToPermissions())
        }

        return collectionModelResponse
    }

}