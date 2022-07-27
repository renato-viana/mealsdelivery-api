package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.validation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [FileSizeValidator::class])
annotation class FileSize(

    val message: String = "tamanho do arquivo inválido",

    val groups: Array<KClass<Any>> = [],

    val payload: Array<KClass<Payload>> = [],

    val max: String

)