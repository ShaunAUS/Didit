package snoopy.didit.api.google

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import snoopy.didit.api.google.client.GoogleOauthClient

private const val GRANT_TYPE = "authorization_code"

@Component
class GoogleOauth(
    @Value("\${oauth2.google.client-id}")
    private var clientId: String,
    @Value("\${oauth2.google.client-secret}")
    private var clientSecret: String,
    @Value("\${oauth2.google.redirect-uri}")
    private var redirectUri: String,
    @Value("\${oauth2.google.token-uri}")
    private var tokenUri: String,
    @Value("\${oauth2.google.resource-uri}")
    private var resourceUri: String,
    private val googleOauthClient: GoogleOauthClient,
) {
    fun getTokenFromGoogleOauthServer(authorizationCode: String): String {
        val googleToken =
            googleOauthClient.googleGetToken(
                authorizationCode,
                clientId,
                clientSecret,
                redirectUri,
                GRANT_TYPE,
            )
        return googleToken.accessToken
    }
}
