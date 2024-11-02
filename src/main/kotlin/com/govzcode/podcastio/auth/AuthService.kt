package com.govzcode.podcastio.auth

import com.govzcode.podcastio.entity.Role
import com.govzcode.podcastio.entity.User
import com.govzcode.podcastio.model.CustomUserDetails
import com.govzcode.podcastio.service.UserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class AuthService(
    private val userService: UserService,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager
) {

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    fun signUp(request: SignRequest): JwtAuthenticationResponse {
        val user = User(
            username = request.username,
            password = passwordEncoder.encode(request.password),
            role = Role.USER
        )
        userService.create(user)
        val jwt = jwtService.generateToken(CustomUserDetails(user))
        return JwtAuthenticationResponse(jwt)
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    fun signIn(request: SignRequest): JwtAuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.username,
                request.password
            )
        )
        val user = userService
            .loadUserByUsername(request.username)
        val jwt = jwtService.generateToken(user)
        return JwtAuthenticationResponse(jwt)
    }
}