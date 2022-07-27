package br.edu.faeterj.petropolis.tcc.mealsdelivery.api.helper

import org.springframework.http.HttpHeaders
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import javax.servlet.http.HttpServletResponse

object ResourceUriHelper {

    fun addUriInResponseHeader(resourceId: Any?) {
        val uri: URI = ServletUriComponentsBuilder.fromCurrentRequestUri()
            .path("/{id}")
            .buildAndExpand(resourceId).toUri()

        val response: HttpServletResponse? =
            (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).response

        response?.setHeader(HttpHeaders.LOCATION, uri.toString())
    }

}