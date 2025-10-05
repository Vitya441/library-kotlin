package by.modsen.libraryapp.security

import by.modsen.libraryapp.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class JpaUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository
            .findByUsername(username)
            .orElseThrow { RuntimeException("User with username: $username not found") }

        return UserPrincipal(user.id!!, user.username, user.password, user.role)
    }
}