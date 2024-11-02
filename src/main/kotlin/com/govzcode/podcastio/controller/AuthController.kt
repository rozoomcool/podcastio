package com.govzcode.podcastio.controller

import com.govzcode.podcastio.auth.AuthService
import com.govzcode.podcastio.auth.JwtAuthenticationResponse
import com.govzcode.podcastio.auth.SignRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
class AuthController(
    private val authenticationService: AuthService
) {
    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/create")
    fun signUp(@RequestBody @Valid request: SignRequest): JwtAuthenticationResponse {
        return authenticationService.signUp(request)
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/login")
    fun signIn(@RequestBody @Valid request: SignRequest): JwtAuthenticationResponse {
        return authenticationService.signIn(request)
    }
}