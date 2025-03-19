package snoopy.didit.domain.member

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import snoopy.didit.email.EMAIL_VERIFICATION_FORM
import snoopy.didit.exception.BusinessException
import snoopy.didit.member.EmailVerifierRepository
import snoopy.didit.template.ErrorCode
import kotlin.random.Random

@Component
class EmailVerifier(
    private val emailVerifierRepository: EmailVerifierRepository,
    private val mailSender: JavaMailSender,
    private val memberFinder: MemberFinder,
) {
    private companion object {
        const val VERIFICATION_CODE_LENGTH = 6
        const val RANDOM_NUMBER_BOUND = 10
    }

    fun startEmailVerification(email: String) {
        memberFinder.checkExistEmail(email)
        val verificationCode = generateVerificationCode()
        sendVerificationEmail(email, verificationCode)
        emailVerifierRepository.saveVerificationCode(email, verificationCode)
    }

    fun verifyCode(
        email: String,
        code: String,
    ) {
        if (!emailVerifierRepository.verifyCode(
                email = email,
                code = code,
            )
        ) {
            throw BusinessException(ErrorCode.INVALID_VERIFICATION_CODE)
        }
    }

    private fun generateVerificationCode(): String {
        return buildString {
            repeat(VERIFICATION_CODE_LENGTH) {
                append(Random.nextInt(RANDOM_NUMBER_BOUND))
            }
        }
    }

    private fun sendVerificationEmail(
        email: String,
        verificationCode: String,
    ) {
        val message = mailSender.createMimeMessage()
        MimeMessageHelper(message, false, "UTF-8")
            .apply {
                setTo(email)
                setSubject("[Didit] 이메일 주소 인증을 완료해 주세요")
                setText(EMAIL_VERIFICATION_FORM.replace("%code%", verificationCode), true)
            }

        mailSender.send(message)
    }
}
