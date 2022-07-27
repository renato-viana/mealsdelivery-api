package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.ShallowEtagHeaderFilter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.Filter

@Configuration
class WebConfig : WebMvcConfigurer {

    @Bean
    fun shallowEtagHeaderFilter(): Filter {
        return ShallowEtagHeaderFilter()
    }

}