package by.modsen.libraryapp.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDate

@Entity
class Loan(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    val book: Book,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id", nullable = false)
    val reader: Reader,

    val loanDate: LocalDate = LocalDate.now(),

    val dueDate: LocalDate,

    var isReturned: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Loan) return false

        if (isReturned != other.isReturned) return false
        if (book != other.book) return false
        if (reader != other.reader) return false
        if (loanDate != other.loanDate) return false
        if (dueDate != other.dueDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isReturned.hashCode()
        result = 31 * result + book.hashCode()
        result = 31 * result + reader.hashCode()
        result = 31 * result + loanDate.hashCode()
        result = 31 * result + dueDate.hashCode()
        return result
    }

    override fun toString(): String {
        return "Loan(id=$id, book=$book, reader=$reader, loanDate=$loanDate, dueDate=$dueDate, isReturned=$isReturned)"
    }
}