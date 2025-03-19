package snoopy.didit.api.auth

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import snoopy.didit.api.auth.annotation.UserId
import snoopy.didit.security.token.TokenResponse
import snoopy.didit.template.Response

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/login")
    fun login(
        @Valid @RequestBody request: LoginRequest,
    ): Response<TokenResponse> {
        val tokenResponse = authService.emailLogin(request)
        return Response.success(tokenResponse)
    }

    @PostMapping("/logout")
    fun logout(
        @UserId userId: Long,
    ): Response<Boolean> {
        val result = authService.logout(userId)
        return Response.success(result)
    }

    @PostMapping("/reissue")
    fun reIssue(
        @Valid @RequestBody request: ReIssueRequest,
    ): Response<TokenResponse> {
        val result = authService.reIssue(request)
        return Response.success(result)
    }
}
