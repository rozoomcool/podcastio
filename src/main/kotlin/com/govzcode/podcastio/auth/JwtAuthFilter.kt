package com.govzcode.podcastio.auth

import com.govzcode.podcastio.service.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.lang.NonNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    private val jwtService: JwtService,
    private val userService: UserService,
    private val logger: Logger = LoggerFactory.getLogger(JwtAuthFilter::class.java)
) : OncePerRequestFilter() {

    companion object {
        const val BEARER_PREFIX = "Bearer "
        const val HEADER_NAME = "Authorization"
    }

    override fun doFilterInternal(
        @NonNull request: HttpServletRequest,
        @NonNull response: HttpServletResponse,
        @NonNull filterChain: FilterChain
    ) {
        this.logger.info("JWT FILTER, SOME REQUEST TRY")

        val authHeader: String? = request.getHeader(HEADER_NAME)
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            this.logger.info("JWT FILTER, REQUEST HAS NO HEADER")
            filterChain.doFilter(request, response)
            return
        }

        val jwt = authHeader.substring(BEARER_PREFIX.length)
        val username = jwtService.extractUsername(jwt)

        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = userService.loadUserByUsername(username)

            if (jwtService.isTokenValid(jwt, userDetails)) {
                val context = SecurityContextHolder.createEmptyContext()

                val authToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )

                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                context.authentication = authToken
                SecurityContextHolder.setContext(context)
                this.logger.info("JWT FILTER, USER AUTHENTICATED")
            }
        }

        filterChain.doFilter(request, response)
    }
}