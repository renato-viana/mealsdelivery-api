package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.email

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotNull

@Validated
@Component
@ConfigurationProperties("mealsdelivery.email")
class EmailProperties(

    @NotNull
    var sender: String? = null,
    var impl: Implementation = Implementation.SMTP,
    var sandbox: Sandbox = Sandbox()

) {

    data class Sandbox(
        var recipient: String? = null
    )

    enum class Implementation {
        SMTP, SANDBOX
    }
}