package by.modsen.libraryapp.repository

import by.modsen.libraryapp.entity.Reservation
import by.modsen.libraryapp.enumeration.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReservationRepository : JpaRepository<Reservation, Long?> {

    fun findByReaderId(readerId: Long): List<Reservation>

    fun findByIdAndStatus(id: Long, status: OrderStatus): Optional<Reservation>
}
