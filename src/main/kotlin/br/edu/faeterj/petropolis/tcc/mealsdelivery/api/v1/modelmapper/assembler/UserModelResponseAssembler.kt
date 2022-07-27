package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller.UserController
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.helper.ResourceLinkHelper
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.UserModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.Security
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.User
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.stereotype.Component

@Component
class UserModelResponseAssembler(

    @Autowired
    private val modelMapper: ModelMapper,

    @Autowired
    private val resourceLinkHelper: ResourceLinkHelper,

    @Autowired
    private val security: Security

) : RepresentationModelAssemblerSupport<User, UserModelResponse>(
    UserController::class.java, UserModelResponse::class.java
) {

    override fun toModel(user: User): UserModelResponse {
        val userModelResponse: UserModelResponse = createModelWithId(user.id ?: -1L, user)

        modelMapper.map(user, userModelResponse)

        if (security.canConsultUsersRolesPermissions()) {
            userModelResponse.add(resourceLinkHelper.linkToUsers("users"))
            userModelResponse.add(resourceLinkHelper.linkToUserRoles(user.id, "user-roles"))
        }

        return userModelResponse
    }

    override fun toCollectionModel(entities: Iterable<User?>): CollectionModel<UserModelResponse> {
        return super.toCollectionModel(entities).add(resourceLinkHelper.linkToUsers())
    }

}