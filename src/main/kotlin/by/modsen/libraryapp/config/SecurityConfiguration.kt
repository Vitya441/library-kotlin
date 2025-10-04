package by.modsen.libraryapp.config

import by.modsen.libraryapp.security.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            authorizeHttpRequests {
                // Разрешаем всем доступ к регистрации/входу и публичным ресурсам
                authorize("/api/v1/auth/**", permitAll)
                authorize("/public/**", permitAll)
                // Любой другой запрос требует аутентификации
                authorize(anyRequest, authenticated)
            }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS // Использование JWT
            }
            // Отключаем httpBasic, если он не нужен
            httpBasic { disable() }

            // DSL-синтаксис для addFilterBefore
            addFilterBefore<UsernamePasswordAuthenticationFilter>(jwtAuthenticationFilter)
        }
        // В отличие от Java, в Kotlin DSL необходимо явно вызвать http.build()
        return http.build()
    }

//    @Bean
//    fun authenticationProvider(): AuthenticationProvider {
//        val provider = DaoAuthenticationProvider()
//        provider.setUserDetailsService(jpaUserDetailsService)
//        provider.setPasswordEncoder(passwordEncoder())
//        return provider
//    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
