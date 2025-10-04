package by.modsen.libraryapp.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userDetailsService: JpaUserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val jwt = authHeader.substring(7)

        try {
            val username = jwtService.extractUsername(jwt)

            if (username != null && SecurityContextHolder.getContext().authentication == null) {
                // Загружаем UserDetails (UserPrincipal) из БД по username
                val userDetails = this.userDetailsService.loadUserByUsername(username)

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // Создаем токен аутентификации. userDetails - это наш UserPrincipal с ID!
                    val authToken = UsernamePasswordAuthenticationToken(
                        userDetails, // <-- Сюда идет UserPrincipal с ID
                        null,
                        userDetails.authorities
                    )

                    authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authToken
                }
            }
        } catch (e: Exception) {
            logger.warn("JWT validation failed: ${e.message}")
            // Важно: в реальном приложении лучше использовать AuthenticationEntryPoint
            // для обработки ошибок 401.
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write("Invalid or expired JWT token: ${e.message}")
            return // Прерываем цепочку фильтров
        }

        filterChain.doFilter(request, response)
    }
}
