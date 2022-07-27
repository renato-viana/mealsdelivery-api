package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.PasswordModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.UserModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.UserWithPasswordModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.UserModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler.UserModelResponseAssembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.disassembler.UserModelRequestDisassembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller.UserControllerOpenApi
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.CheckSecurity
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.User
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.UserRepository
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(path = ["/v1/users"])
class UserController(

    @Autowired
    private val userRepository: UserRepository,

    @Autowired
    private val userService: UserService,

    @Autowired
    private val userModelResponseAssembler: UserModelResponseAssembler,

    @Autowired
    private val userModelRequestDisassembler: UserModelRequestDisassembler

) : UserControllerOpenApi {

    @CheckSecurity.UsersRolesPermissions.CanConsult
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun list(): CollectionModel<UserModelResponse> {
        val users: List<User> = userRepository.findAll()

        return userModelResponseAssembler.toCollectionModel(users)
    }

    @CheckSecurity.UsersRolesPermissions.CanConsult
    @GetMapping(path = ["/{userId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun fetch(@PathVariable userId: Long?): UserModelResponse {
        val user: User = userService.fetchOrFail(userId)

        return userModelResponseAssembler.toModel(user)
    }

    @CheckSecurity.UsersRolesPermissions.CanEdit
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    override fun add(@RequestBody @Valid userWithPasswordModelRequest: UserWithPasswordModelRequest): UserModelResponse {
        var user: User = userModelRequestDisassembler.toDomainObject(userWithPasswordModelRequest)
        user = userService.save(user)

        return userModelResponseAssembler.toModel(user)
    }

    @CheckSecurity.UsersRolesPermissions.CanChangeUser
    @PutMapping(path = ["/{userId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun update(
        @PathVariable userId: Long,
        @RequestBody @Valid userModelRequest: UserModelRequest
    ): UserModelResponse {
        var currentUser: User = userService.fetchOrFail(userId)

        userModelRequestDisassembler.copyToDomainObject(userModelRequest, currentUser)

        currentUser = userService.save(currentUser)

        return userModelResponseAssembler.toModel(currentUser)
    }

    @CheckSecurity.UsersRolesPermissions.CanChangeOwnPassword
    @PutMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun changePassword(
        @PathVariable userId: Long,
        @RequestBody @Valid passwordModelRequest: PasswordModelRequest
    ) {
        userService.changePassword(userId, passwordModelRequest.currentPassword, passwordModelRequest.newPassword)
    }

}