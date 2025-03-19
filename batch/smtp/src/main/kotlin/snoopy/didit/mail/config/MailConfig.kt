package snoopy.didit.mail.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.Properties

private const val MAIL_SMTP_AUTH = "mail.smtp.auth"
private const val MAIL_DEBUG = "mail.smtp.debug"
private const val MAIL_CONNECTION_TIMEOUT = "mail.smtp.connectiontimeout"
private const val MAIL_TIMEOUT = "mail.smtp.timeout"
private const val MAIL_WRITE_TIMEOUT = "mail.smtp.writetimeout"
private const val MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable"

@Configuration
class MailConfig(
    @Value("\${spring.mail.host}")
    private val host: String,
    @Value("\${spring.mail.username}")
    private val username: String,
    @Value("\${spring.mail.password}")
    private val password: String,
    @Value("\${spring.mail.port}")
    private val port: Int,
    @Value("\${spring.mail.properties.mail.smtp.auth}")
    private val auth: Boolean,
    @Value("\${spring.mail.properties.mail.smtp.debug}")
    private val debug: Boolean,
    @Value("\${spring.mail.properties.mail.smtp.connectiontimeout}")
    private val connectionTimeout: Int,
    @Value("\${spring.mail.properties.mail.smtp.timeout}")
    private val timeout: Int,
    @Value("\${spring.mail.properties.mail.smtp.writetimeout}")
    private val writeTimeout: Int,
    @Value("\${spring.mail.properties.mail.smtp.starttls.enable}")
    private val startTlsEnable: Boolean,
) {
    @Bean
    fun javaMailService(): JavaMailSender {
        val javaMailSender =
            JavaMailSenderImpl().apply {
                host = this@MailConfig.host
                username = this@MailConfig.username
                password = this@MailConfig.password
                port = this@MailConfig.port
                defaultEncoding = "UTF-8"

                javaMailProperties =
                    Properties().apply {
                        put(MAIL_SMTP_AUTH, auth)
                        put(MAIL_DEBUG, debug)
                        put(MAIL_CONNECTION_TIMEOUT, connectionTimeout)
                        put(MAIL_TIMEOUT, timeout)
                        put(MAIL_WRITE_TIMEOUT, writeTimeout)
                        put(MAIL_SMTP_STARTTLS_ENABLE, startTlsEnable)
                    }
            }
        return javaMailSender
    }
}
