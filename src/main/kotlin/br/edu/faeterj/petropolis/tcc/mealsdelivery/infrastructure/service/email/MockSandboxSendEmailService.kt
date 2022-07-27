package br.edu.faeterj.petropolis.tcc.mealsdelivery.infrastructure.service.email

import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.email.EmailProperties
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.SendEmailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.MimeMessageHelper
import javax.mail.MessagingException
import javax.mail.internet.MimeMessage

class MockSandboxSendEmailService : SmtpSendEmailService() {

    @Autowired
    private lateinit var emailProperties: EmailProperties

    @Throws(MessagingException::class)
    override fun createMimeMessage(message: SendEmailService.Message): MimeMessage? {
        val mimeMessage: MimeMessage? = super.createMimeMessage(message)

        val helper = mimeMessage?.let { MimeMessageHelper(it, "UTF-8") }
        emailProperties.sandbox.recipient?.let { helper?.setTo(it) }

        return mimeMessage
    }

}