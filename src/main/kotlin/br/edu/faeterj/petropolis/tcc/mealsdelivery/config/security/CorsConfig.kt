package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.security

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    @Bean
    fun corsFilterFilterRegistrationBean(): FilterRegistrationBean<CorsFilter> {
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOriginPattern("*")
        config.allowedOrigins = listOf("http://api.mealsdelivery.local:8080", "http://www.deliveryanalysis.local:8081")
        config.allowedMethods = listOf("*")
        config.allowedHeaders = listOf("*")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)

        val bean = FilterRegistrationBean<CorsFilter>()
        bean.filter = CorsFilter(source)
        bean.order = Ordered.HIGHEST_PRECEDENCE

        return bean
    }

}