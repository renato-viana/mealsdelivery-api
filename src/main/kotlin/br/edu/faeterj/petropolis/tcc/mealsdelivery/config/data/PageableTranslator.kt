package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.data

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import java.util.stream.Collectors

object PageableTranslator {

    fun translate(pageable: Pageable, fieldsMapping: Map<String, String>): Pageable {
        val orders = pageable.sort.stream()
            .filter { order: Sort.Order -> fieldsMapping.containsKey(order.property) }
            .map { order: Sort.Order -> Sort.Order(order.direction, fieldsMapping[order.property].toString()) }
            .collect(Collectors.toList())

        return PageRequest.of(pageable.pageNumber, pageable.pageSize, Sort.by(orders))
    }

}