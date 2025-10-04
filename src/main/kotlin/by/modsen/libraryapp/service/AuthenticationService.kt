package by.modsen.libraryapp.service

import by.modsen.libraryapp.dto.request.LoginRequest
import by.modsen.libraryapp.dto.request.RegisterRequest
import by.modsen.libraryapp.dto.response.AuthResponse

interface AuthenticationService {

    fun register(registerRequest: RegisterRequest): AuthResponse

    fun login(loginRequest: LoginRequest): AuthResponse
}