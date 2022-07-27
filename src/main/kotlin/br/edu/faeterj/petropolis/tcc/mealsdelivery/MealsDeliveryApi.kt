package br.edu.faeterj.petropolis.tcc.mealsdelivery

import br.edu.faeterj.petropolis.tcc.mealsdelivery.infrastructure.repository.CustomJpaRepositoryImpl
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import java.util.*

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl::class)
class MealsDeliveryApi

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))

    runApplication<MealsDeliveryApi>(*args)
}

