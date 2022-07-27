package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.data

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

class PageWrapper<T>(page: Page<T>, private val pageable: Pageable) :
    PageImpl<T>(page.content, pageable, page.totalElements) {

    override fun getPageable(): Pageable {
        return this.pageable
    }

}