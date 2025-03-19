package snoopy.didit.api.google.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import snoopy.didit.domain.google.dto.GoogleResourceDto

@FeignClient(
    name = "GoogleOAuth2",
    url = "https://www.googleapis.com",
)
interface GoogleResourceClient {
    // Access Token을 기반으로 Google 리소스 서버에서 유저 정보를 가져올 API
    @GetMapping(
        value = ["/userinfo/v2/me"],
        consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE],
    )
    fun googleGetResource(
        @RequestHeader("Authorization") accessToken: String,
    ): GoogleResourceDto
}
