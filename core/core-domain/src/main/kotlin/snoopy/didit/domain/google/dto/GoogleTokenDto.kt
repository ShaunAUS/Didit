package snoopy.didit.domain.google.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class GoogleTokenDto(
    @JsonProperty("access_token")
    val accessToken: String,
)
