package by.modsen.libraryapp.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtService {
    @Value("\${app.jwt.secretKey}")
    private val secretKey: String? = null

    @Value("\${app.jwt.expiration}")
    private val jwtExpiration: Long = 0 // Время жизни access токена

    fun extractUsername(token: String): String =
        extractClaim(token, Claims::getSubject)

    // Извлечение ID пользователя (кастомный claim "id")
    fun extractUserId(token: String): Long? = extractClaim(token) { claims ->
        claims.get("userId", Long::class.java)
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String?): Claims {
        return Jwts
            .parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    private fun getSignInKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    /**
     * Генерирует JWT access токен для UserDetails, включая указанный userId.
     */
    fun generateAccessToken(userDetails: UserDetails, userId: Long): String {
        val claims = mutableMapOf<String, Any>()

        // 1. Добавляем ID пользователя (ключевой момент для авторизации)
        claims["userId"] = userId

        // 2. Добавляем Роль
        val role = userDetails.authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .findFirst()
            .orElse("ROLE_READER") // Роль по умолчанию
        claims["role"] = role

        return buildToken(claims, userDetails, jwtExpiration)
    }

    /**
     * Общий метод для генерации JWT токена с любыми дополнительными claims и заданным временем истечения.
     * @param extraClaims Map, содержащая дополнительные claims, которые нужно включить в токен.
     * @param userDetails Объект UserDetails, содержащий основные данные пользователя (username, authorities).
     * @param expiration Время жизни токена в миллисекундах.
     * @return Сгенерированный JWT токен.
     */
    private fun buildToken(
        extraClaims: Map<String, Any>,
        userDetails: UserDetails,
        expiration: Long
    ): String {
        return Jwts
            .builder()
            .claims(extraClaims)
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey())
            .compact()
    }

    /**
     * Валидация
     */
    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return (username == userDetails.username && !isTokenExpired(token))
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date = extractClaim(token, Claims::getExpiration)
}
