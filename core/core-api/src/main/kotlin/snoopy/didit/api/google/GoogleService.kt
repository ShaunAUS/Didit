package snoopy.didit.api.google

import org.springframework.stereotype.Service
import snoopy.didit.api.auth.AuthService
import snoopy.didit.api.google.client.GoogleResourceClient
import snoopy.didit.domain.google.dto.GoogleResourceDto
import snoopy.didit.domain.member.Member
import snoopy.didit.domain.member.MemberService
import snoopy.didit.domain.member.SignUpService
import snoopy.didit.domain.member.dto.SignUpDto
import snoopy.didit.exception.BusinessException
import snoopy.didit.member.JobFamily
import snoopy.didit.member.JobRole
import snoopy.didit.security.token.TokenResponse

private const val TEMP_PASSWORD = "asd12341234"

@Service
class GoogleService(
    private val googleOauth: GoogleOauth,
    private val memberService: MemberService,
    private val signupService: SignUpService,
    private val authService: AuthService,
    private val googleResourceClient: GoogleResourceClient,
) {
    fun googleLogin(authorizationCode: String): TokenResponse {
        val userResourceFromGoogle = getUserResource(getAccessTokenFromGoogleOauth(authorizationCode))
        return try {
            authService.socialLogin(findMemberBy(userResourceFromGoogle.email))
        } catch (e: BusinessException) {
            signUpBy(userResourceFromGoogle)
            authService.socialLogin(findMemberBy(userResourceFromGoogle.email))
        }
    }

    private fun signUpBy(userResourceFromGoogle: GoogleResourceDto) =
        signupService.signUp(createTempSignUpDto(userResourceFromGoogle))

    private fun createTempSignUpDto(userResourceFromGoogle: GoogleResourceDto): SignUpDto {
        return SignUpDto(
            email = userResourceFromGoogle.email,
            password = TEMP_PASSWORD,
            name = userResourceFromGoogle.name,
            nickName = userResourceFromGoogle.name,
            jobFamily = JobFamily.NOT_SELECTED,
            jobRole = JobRole.NOT_SELECTED,
            memberSkills = null,
            alarm = null,
        )
    }

    private fun findMemberBy(email: String): Member {
        return memberService.findByEmail(email)
    }

    private fun getUserResource(accessToken: String): GoogleResourceDto {
        return googleResourceClient.googleGetResource("Bearer $accessToken")
    }

    private fun getAccessTokenFromGoogleOauth(authorizationCode: String) =
        googleOauth.getTokenFromGoogleOauthServer(
            authorizationCode,
        )
}
