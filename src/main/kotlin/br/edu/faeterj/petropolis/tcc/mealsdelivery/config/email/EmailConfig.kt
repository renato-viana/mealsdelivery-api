package br.edu.faeterj.petropolis.tcc.mealsdelivery.config.email

import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.SendEmailService
import br.edu.faeterj.petropolis.tcc.mealsdelivery.infrastructure.service.email.MockSandboxSendEmailService
import br.edu.faeterj.petropolis.tcc.mealsdelivery.infrastructure.service.email.SmtpSendEmailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EmailConfig(

    @Autowired
    private val emailProperties: EmailProperties

) {

    @Bean
    fun sendEmailService(): SendEmailService {
        return when (emailProperties.impl) {
            EmailProperties.Implementation.SANDBOX -> MockSandboxSendEmailService()
            EmailProperties.Implementation.SMTP -> SmtpSendEmailService()
        }
    }

}