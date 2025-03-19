package snoopy.didit.api.google

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import snoopy.didit.security.token.TokenResponse

@RestController
@RequestMapping("/api/v1/google")
class GoogleController(
    private val googleService: GoogleService,
) {
    @GetMapping("/login")
    fun googleLogin(
        @RequestParam code: String,
    ): TokenResponse {
        return googleService.googleLogin(code)
    }
}
