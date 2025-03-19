package snoopy.didit.security.token

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
) {
    constructor(accessToken: AccessToken, refreshToken: RefreshToken) : this(
        accessToken = accessToken.value,
        refreshToken = refreshToken.value,
    )

    companion object {
        fun from(tokens: ReIssueTokenResponse): TokenResponse =
            TokenResponse(
                accessToken = tokens.accessToken.value,
                refreshToken = tokens.refreshToken.value,
            )
    }
}
