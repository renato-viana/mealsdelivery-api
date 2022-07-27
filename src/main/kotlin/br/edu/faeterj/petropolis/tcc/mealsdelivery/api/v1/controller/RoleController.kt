package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.controller

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.RoleModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.response.RoleModelResponse
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.assembler.RoleModelResponseAssembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.disassembler.RoleModelRequestDisassembler
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.openapi.controller.RoleControllerOpenApi
import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.CheckSecurity
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Role
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.repository.RoleRepository
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.RoleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(path = ["/v1/roles"])
class RoleController(

    @Autowired
    private val roleRepository: RoleRepository,

    @Autowired
    private val roleService: RoleService,

    @Autowired
    private val roleModelResponseAssembler: RoleModelResponseAssembler,

    @Autowired
    private val roleModelRequestDisassembler: RoleModelRequestDisassembler

) : RoleControllerOpenApi {

    @CheckSecurity.UsersRolesPermissions.CanConsult
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun list(): CollectionModel<RoleModelResponse> {
        val roles: List<Role> = roleRepository.findAll()

        return roleModelResponseAssembler.toCollectionModel(roles)
    }

    @CheckSecurity.UsersRolesPermissions.CanConsult
    @GetMapping(path = ["/{roleId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun search(@PathVariable roleId: Long): RoleModelResponse {
        val role: Role = roleService.fetchOrFail(roleId)
        return roleModelResponseAssembler.toModel(role)
    }

    @CheckSecurity.UsersRolesPermissions.CanEdit
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    override fun add(@RequestBody @Valid roleModelRequest: RoleModelRequest): RoleModelResponse {
        var role: Role = roleModelRequestDisassembler.toDomainObject(roleModelRequest)
        role = roleService.save(role)
        return roleModelResponseAssembler.toModel(role)
    }

    @CheckSecurity.UsersRolesPermissions.CanEdit
    @PutMapping(path = ["/{roleId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun update(
        @PathVariable roleId: Long,
        @RequestBody @Valid roleModelRequest: RoleModelRequest
    ): RoleModelResponse {
        var currentRole: Role = roleService.fetchOrFail(roleId)
        roleModelRequestDisassembler.copyToDomainObject(roleModelRequest, currentRole)
        currentRole = roleService.save(currentRole)
        return roleModelResponseAssembler.toModel(currentRole)
    }

    @CheckSecurity.UsersRolesPermissions.CanEdit
    @DeleteMapping("/{roleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    override fun remove(@PathVariable roleId: Long) {
        roleService.delete(roleId)
    }

}