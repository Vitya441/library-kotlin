package by.modsen.libraryapp

import io.jsonwebtoken.security.Keys
import java.util.Base64

fun main() {
    val secureKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256)

    // Кодирует его в Base64 (стандартная кодировка, которую ожидает Decoders.BASE64)
    val base64Key = Base64.getEncoder().encodeToString(secureKey.encoded)

    // Выведите этот ключ в консоль и скопируйте
    println(base64Key)
}