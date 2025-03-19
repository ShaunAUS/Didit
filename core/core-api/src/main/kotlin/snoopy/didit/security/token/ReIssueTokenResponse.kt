package snoopy.didit.security.token

data class ReIssueTokenResponse(
    val accessToken: AccessToken,
    val refreshToken: RefreshToken,
)
