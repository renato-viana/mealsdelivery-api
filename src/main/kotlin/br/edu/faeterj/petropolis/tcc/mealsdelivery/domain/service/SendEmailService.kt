package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Order

interface SendEmailService {

    fun send(message: Message)

    data class Message(

        val recipients: MutableSet<String?>,

        val subject: String,

        val body: String,

        val variables: MutableMap<String, Order> = mutableMapOf()

    )

}