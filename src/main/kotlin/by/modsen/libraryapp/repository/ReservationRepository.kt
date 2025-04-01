package by.modsen.libraryapp.repository

import by.modsen.libraryapp.entity.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReservationRepository : JpaRepository<Reservation, Long?> {

    fun findByReaderId(readerId: Long): List<Reservation>
}
