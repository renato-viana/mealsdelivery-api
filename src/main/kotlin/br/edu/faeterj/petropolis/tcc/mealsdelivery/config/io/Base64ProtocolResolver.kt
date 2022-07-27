package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.io

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.ProtocolResolver
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import java.util.*

@Component
class Base64ProtocolResolver : ProtocolResolver, ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
        configurableApplicationContext.addProtocolResolver(this)
    }

    override fun resolve(location: String, resourceLoader: ResourceLoader): Resource? {
        if (location.startsWith("base64:")) {
            val decodedResource = Base64.getDecoder().decode(location.substring(7))

            return ByteArrayResource(decodedResource)
        }
        return null
    }

}