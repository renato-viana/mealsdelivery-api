package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security

import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import java.util.stream.Collectors

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class ResourceServerConfig : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .formLogin()
            .loginPage("/login")
            .and()
            .authorizeRequests()
            .antMatchers("/oauth/**").authenticated()
            .and()
            .csrf().disable()
            .cors()
            .and()
            .oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(jwtAuthenticationConverter())
    }

    private fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val jwtAuthenticationConverter = JwtAuthenticationConverter()
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter { jwt: Jwt ->
            var authorities = jwt.getClaimAsStringList("authorities")

            if (authorities == null) {
                authorities = emptyList()
            }

            val scopesAuthoritiesConverter = JwtGrantedAuthoritiesConverter()

            val grantedAuthorities = scopesAuthoritiesConverter.convert(jwt)
            grantedAuthorities?.addAll(authorities.stream()
                .map { role: String -> SimpleGrantedAuthority(role) }
                .collect(Collectors.toList())
            )

            grantedAuthorities
        }

        return jwtAuthenticationConverter
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

}