package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security.authorizationserver

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class SecurityController {

    @GetMapping("/login")
    fun login(): String {
        return "pages/login"
    }

    @GetMapping("/oauth/confirm_access")
    fun approval(): String {
        return "pages/approval"
    }

}