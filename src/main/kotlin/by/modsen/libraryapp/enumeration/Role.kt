package by.modsen.libraryapp.enumeration

import org.springframework.security.core.GrantedAuthority

enum class Role : GrantedAuthority {
    READER,
    LIBRARIAN,
    ADMIN;

    override fun getAuthority(): String {
        return "ROLE_$name" // Формат, требуемый Spring Security
    }
}