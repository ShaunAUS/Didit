package snoopy.didit.member

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import snoopy.didit.domain.member.EmailVerifier
import snoopy.didit.domain.member.SignUpService
import snoopy.didit.domain.member.dto.SignUpDto
import snoopy.didit.swagger.annotation.ApiDoc
import snoopy.didit.template.Response

@RestController
@RequestMapping("/api/v1")
@Tag(name = "SignUpController", description = "회원가입 API")
class SignUpController(
    private val signUpService: SignUpService,
    private val emailVerifier: EmailVerifier,
) {
    @ApiDoc(
        summary = "회원가입",
        description = "회원가입을 한다",
        success = "signUp.success.response",
        error = "signUp.error.response",
    )
    @PostMapping("/signup")
    fun signUp(
        @RequestBody @Valid signUpDto: SignUpDto,
    ): Response<Unit> {
        signUpService.signUp(signUpDto)
        return Response.success(Unit)
    }

    @ApiDoc(
        summary = "이메일 인증",
        description = "이메일 인증을 한다",
        success = "signUp.email.verification.success.response",
        error = "space.email.verification.error.response",
    )
    @PostMapping("/email-verification")
    fun emailVerification(
        @Parameter(description = "이메일", example = "didit@gmail.com", required = true)
        @RequestParam email: String,
    ): Response<Unit> {
        emailVerifier.startEmailVerification(email)
        return Response.success(Unit)
    }

    @ApiDoc(
        summary = "이메일 인증 코드 확인",
        description = "이메일 인증 코드를 확인한다",
        success = "signUp.email.verify.code.success.response",
        error = "signUp.email.verify.code.error.response",
    )
    @GetMapping("/email-verification")
    fun verifyCode(
        @Parameter(description = "이메일", example = "didit@gmail.com", required = true)
        @RequestParam email: String,
        @Parameter(description = "인증코드", example = "5X24yKa75", required = true)
        @RequestParam code: String,
    ): Response<Unit> {
        emailVerifier.verifyCode(email, code)
        return Response.success(Unit)
    }
}
