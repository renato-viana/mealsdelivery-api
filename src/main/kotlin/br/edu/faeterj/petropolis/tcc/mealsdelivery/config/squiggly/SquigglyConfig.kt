package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.squiggly

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.bohnman.squiggly.Squiggly
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider
import com.github.bohnman.squiggly.web.SquigglyRequestFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SquigglyConfig {

    @Bean
    fun squigglyRequestFilter(objectMapper: ObjectMapper?): FilterRegistrationBean<SquigglyRequestFilter> {
        Squiggly.init(objectMapper, RequestSquigglyContextProvider("fields", null))

        val urlPatterns = listOf("/orders/*", "/restaurants/*")

        val filterRegistration = FilterRegistrationBean<SquigglyRequestFilter>()
        filterRegistration.filter = SquigglyRequestFilter()
        filterRegistration.order = 1
        filterRegistration.urlPatterns = urlPatterns

        return filterRegistration
    }

}