package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.disassembler

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.RoleModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Role
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RoleModelRequestDisassembler(

    @Autowired
    private val modelMapper: ModelMapper
) {

    fun toDomainObject(roleModelRequest: RoleModelRequest?): Role {
        return modelMapper.map(roleModelRequest, Role::class.java)
    }

    fun copyToDomainObject(roleModelRequest: RoleModelRequest, role: Role) {
        modelMapper.map(roleModelRequest, role)
    }

}