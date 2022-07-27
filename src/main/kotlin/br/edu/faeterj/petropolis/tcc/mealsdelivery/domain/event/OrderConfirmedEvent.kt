package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.event

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Order

data class OrderConfirmedEvent(val order: Order)