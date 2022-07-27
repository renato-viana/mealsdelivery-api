package br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.event

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.model.Order

data class OrderCanceledEvent(var order: Order)