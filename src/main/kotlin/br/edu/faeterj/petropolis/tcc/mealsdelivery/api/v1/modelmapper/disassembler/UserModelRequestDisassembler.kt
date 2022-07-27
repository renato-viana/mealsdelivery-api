package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.modelmapper.disassembler

import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.UserModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.api.v1.model.request.UserWithPasswordModelRequest
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.User
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.validation.Valid

@Component
class UserModelRequestDisassembler(

    @Autowired
    private val modelMapper: ModelMapper

) {

    fun toDomainObject(userModelRequest: UserModelRequest): User {
        return modelMapper.map(userModelRequest, User::class.java)
    }

    fun copyToDomainObject(userModelRequest: UserModelRequest, user: User) {
        modelMapper.map(userModelRequest, user)
    }

}