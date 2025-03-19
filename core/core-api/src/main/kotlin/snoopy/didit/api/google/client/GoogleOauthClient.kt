package snoopy.didit.api.google.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import snoopy.didit.domain.google.dto.GoogleResourceDto
import snoopy.didit.domain.google.dto.GoogleTokenDto

@FeignClient(
    name = "GoogleOAuth",
    url = "https://oauth2.googleapis.com",
)
interface GoogleOauthClient {
    // Access Token을 기반으로 Google 리소스 서버에서 유저 정보를 가져올 API
    @GetMapping(
        value = ["/oauth2/v2/userinfo/v2/me"],
        consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE],
    )
    fun googleGetResource(
        @RequestHeader("Authorization") accessToken: String,
    ): GoogleResourceDto

    @PostMapping(
        value = ["/token"],
        consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE],
    )
    fun googleGetToken(
        @RequestParam("code") code: String,
        @RequestParam("client_id") clientId: String,
        @RequestParam("client_secret") clientSecret: String,
        @RequestParam("redirect_uri") redirectUri: String,
        @RequestParam("grant_type") grantType: String = "authorization_code",
    ): GoogleTokenDto
}
