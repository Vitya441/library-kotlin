package by.modsen.libraryapp.service.impl

import by.modsen.libraryapp.dto.request.LoginRequest
import by.modsen.libraryapp.dto.request.RegisterRequest
import by.modsen.libraryapp.dto.response.AuthResponse
import by.modsen.libraryapp.entity.User
import by.modsen.libraryapp.enumeration.Role
import by.modsen.libraryapp.repository.UserRepository
import by.modsen.libraryapp.security.JwtService
import by.modsen.libraryapp.security.UserPrincipal
import by.modsen.libraryapp.service.AuthenticationService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationServiceImpl(
    private val repository: UserRepository,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,

    ) : AuthenticationService {

    override fun register(registerRequest: RegisterRequest): AuthResponse {
        if (repository.existsByUsername(registerRequest.username)) {
            throw RuntimeException("User with username: ${registerRequest.username} already exists")
        }

        val user = User(
            username = registerRequest.username,
            password = passwordEncoder.encode(registerRequest.password),
            firstName = registerRequest.firstName,
            lastName = registerRequest.lastName,
            role = Role.READER
        )

        repository.save(user)

        val userPrincipal = createUserPrincipal(user)
        val jwtToken = jwtService.generateAccessToken(userPrincipal, userPrincipal.id)

        return AuthResponse(token = jwtToken)
    }

    override fun login(loginRequest: LoginRequest): AuthResponse {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.username,
                loginRequest.password
            )
        )

        val userPrincipal = authentication.principal as UserPrincipal
        val jwtToken = jwtService.generateAccessToken(userPrincipal, userPrincipal.id)

        return AuthResponse(jwtToken)
    }

    private fun createUserPrincipal(user: User): UserPrincipal {
        return UserPrincipal(
            id = user.id!!,
            usernameValue = user.username,
            passwordValue = user.password,
            role = user.role
        )
    }
}
