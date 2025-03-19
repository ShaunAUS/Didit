package snoopy.didit.fixture

import snoopy.didit.security.token.TokenResponse

class GoogleControllerTestFixture {
    companion object {
        fun getGoogleLoginResponse(): TokenResponse {
            return TokenResponse(
                accessToken = "accessToken",
                refreshToken = "refreshToken",
            )
        }
    }
}
