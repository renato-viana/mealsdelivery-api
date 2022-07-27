package br.edu.faeterj.petropolis.tcc.mealsdelivery.infrastructure.service.email

import br.edu.faeterj.petropolis.tcc.mealsdelivery.config.email.EmailProperties
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.SendEmailService
import br.edu.faeterj.petropolis.tcc.mealsdelivery.domain.service.SendEmailService.Message
import freemarker.template.Configuration
import freemarker.template.Template
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils
import javax.mail.MessagingException
import javax.mail.internet.MimeMessage

open class SmtpSendEmailService : SendEmailService {

    @Autowired
    private lateinit var mailSender: JavaMailSender

    @Autowired
    private lateinit var emailProperties: EmailProperties

    @Autowired
    private lateinit var freemarkerConfig: Configuration

    override fun send(message: Message) {
        try {
            val mimeMessage: MimeMessage? = createMimeMessage(message)

            mailSender.send(mimeMessage)
        } catch (e: Exception) {
            throw EmailException("Não foi possível enviar e-mail", e)
        }
    }

    @Throws(MessagingException::class)
    protected open fun createMimeMessage(message: Message): MimeMessage? {
        val body = processTemplate(message)

        val mimeMessage: MimeMessage = mailSender.createMimeMessage()

        val helper = MimeMessageHelper(mimeMessage, "UTF-8")
        emailProperties.sender?.let { helper.setFrom(it) }
        message.recipients.first()?.let { helper.setTo(it) }
        helper.setSubject(message.subject)
        helper.setText(body, true)

        return mimeMessage
    }

    private fun processTemplate(message: Message): String {
        return try {
            val template: Template = freemarkerConfig.getTemplate(message.body)

            FreeMarkerTemplateUtils.processTemplateIntoString(template, message.variables)
        } catch (e: Exception) {
            throw EmailException("Não foi possível montar o template do e-mail", e)
        }
    }

}