package by.modsen.libraryapp.security

import by.modsen.libraryapp.enumeration.Role
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UserPrincipal(
    val id: Long, // Самый важный элемент
    val usernameValue: String,
    val passwordValue: String,
    val role: Role
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf(role)

    override fun getUsername(): String = usernameValue

    override fun getPassword(): String = passwordValue
}
