package by.modsen.libraryapp.repository

import by.modsen.libraryapp.entity.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * TODO: Вторым параметром в <T, ID> надо передать точный тип ID сущности,
 *       в моём случае это LONG NULLABLE (val id: Long?)
 */
@Repository
interface ReservationRepository : JpaRepository<Reservation, Long?>
