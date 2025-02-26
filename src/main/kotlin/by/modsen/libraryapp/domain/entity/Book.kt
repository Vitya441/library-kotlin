package by.modsen.libraryapp.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val genre: String,

    @Column(nullable = false)
    val author: String,

    @Column(nullable = false)
    var totalCopies: Int,

    @Column(nullable = false)
    var availableCopies: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Book) return false

        if (totalCopies != other.totalCopies) return false
        if (availableCopies != other.availableCopies) return false
        if (title != other.title) return false
        if (genre != other.genre) return false
        if (author != other.author) return false

        return true
    }

    override fun hashCode(): Int {
        var result = totalCopies
        result = 31 * result + availableCopies
        result = 31 * result + title.hashCode()
        result = 31 * result + genre.hashCode()
        result = 31 * result + author.hashCode()
        return result
    }

    override fun toString(): String {
        return "Book(id=$id, title='$title', genre='$genre', author='$author', totalCopies=$totalCopies, availableCopies=$availableCopies)"
    }
}