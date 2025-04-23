package by.modsen.libraryapp.entity

import by.modsen.libraryapp.enumeration.OrderStatus
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    val book: Book,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id", nullable = false)
    val reader: Reader,

    val reservationDate: LocalDate = LocalDate.now(),

    @Convert(converter = OrderStatusConverter::class)
    var status: OrderStatus = OrderStatus.PENDING

) : BaseEntity()
