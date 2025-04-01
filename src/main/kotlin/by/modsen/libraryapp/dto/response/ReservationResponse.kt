package by.modsen.libraryapp.dto.response

import by.modsen.libraryapp.enumeration.OrderStatus
import java.time.LocalDate

data class ReservationResponse(
    var id: Long?,
    val book: BookResponse,
    val reader: ReaderResponse,
    val reservationDate: LocalDate,
    var status: OrderStatus = OrderStatus.PENDING,
)