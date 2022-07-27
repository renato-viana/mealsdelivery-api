package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.authorizationserver

import com.nimbusds.jose.jwk.JWKSet
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class JwkSetController(

    @Autowired
    var jwkSet: JWKSet

) {

    @GetMapping("/.well-known/jwks.json")
    fun keys(): Map<String, Any> {
        return jwkSet.toJSONObject()
    }

}