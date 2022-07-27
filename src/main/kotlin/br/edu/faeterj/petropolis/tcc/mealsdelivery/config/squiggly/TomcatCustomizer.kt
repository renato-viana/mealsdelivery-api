package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.squiggly

import org.apache.catalina.connector.Connector
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.stereotype.Component

@Component
class TomcatCustomizer : WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    override fun customize(factory: TomcatServletWebServerFactory) {
        factory.addConnectorCustomizers(TomcatConnectorCustomizer { connector: Connector ->
            connector.setProperty(
                "relaxedQueryChars",
                "[]"
            )
        })
    }

}