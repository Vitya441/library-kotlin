package by.modsen.libraryapp.domain.entity

import by.modsen.libraryapp.domain.enumeration.OrderStatus
import by.modsen.libraryapp.util.OrderStatusConverter
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDate

@Entity
class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    val book: Book,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id", nullable = false)
    val reader: Reader,

    val reservationDate: LocalDate = LocalDate.now(),

    @Convert(converter = OrderStatusConverter::class)
    val status: OrderStatus = OrderStatus.PENDING,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Reservation) return false

        if (book != other.book) return false
        if (reader != other.reader) return false
        if (reservationDate != other.reservationDate) return false
        if (status != other.status) return false

        return true
    }

    override fun hashCode(): Int {
        var result = book.hashCode()
        result = 31 * result + reader.hashCode()
        result = 31 * result + reservationDate.hashCode()
        result = 31 * result + status.hashCode()
        return result
    }

    override fun toString(): String {
        return "Reservation(id=$id, book=$book, reader=$reader, reservationDate=$reservationDate, status=$status)"
    }
}