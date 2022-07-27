package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.springframework.boot.jackson.JsonComponent
import org.springframework.data.domain.Page
import java.io.IOException

@JsonComponent
class PageJsonSerializer : JsonSerializer<Page<*>>() {

    @Throws(IOException::class)
    override fun serialize(page: Page<*>, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeStartObject()
        gen.writeObjectField("content", page.content)
        gen.writeNumberField("size", page.size)
        gen.writeNumberField("totalElements", page.totalElements)
        gen.writeNumberField("totalPages", page.totalPages)
        gen.writeNumberField("number", page.number)
        gen.writeEndObject()
    }

}