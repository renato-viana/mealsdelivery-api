package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.authorizationserver

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Validated
@Component
@ConfigurationProperties("mealsdelivery.jwt.keystore")
data class JwtKeyStoreProperties(

    var jksLocation: @NotNull Resource? = null,

    var password: @NotBlank String? = "",

    var keypairAlias: @NotBlank String? = ""

)