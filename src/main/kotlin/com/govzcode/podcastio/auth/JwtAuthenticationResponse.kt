package com.govzcode.podcastio.auth

import io.swagger.v3.oas.annotations.media.Schema


@Schema(description = "Ответ c токеном доступа")
class JwtAuthenticationResponse(
    @Schema(description = "Токен доступа", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyMjUwNj...")
    private val token: String? = null
)