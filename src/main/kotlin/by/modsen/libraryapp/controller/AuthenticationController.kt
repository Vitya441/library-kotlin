package by.modsen.libraryapp.controller

import by.modsen.libraryapp.dto.request.LoginRequest
import by.modsen.libraryapp.dto.request.RegisterRequest
import by.modsen.libraryapp.dto.response.AuthResponse
import by.modsen.libraryapp.service.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController(private val service: AuthenticationService) {

    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest): AuthResponse {
        return service.register(registerRequest)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): AuthResponse {
        return service.login(loginRequest)
    }
}
