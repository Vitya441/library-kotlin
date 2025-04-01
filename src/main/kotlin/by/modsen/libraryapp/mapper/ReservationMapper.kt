package by.modsen.libraryapp.mapper

import by.modsen.libraryapp.dto.response.ReservationResponse
import by.modsen.libraryapp.entity.Reservation
import org.mapstruct.Mapper

@Mapper(componentModel = "spring", uses = [ReaderMapper::class, BookMapper::class])
interface ReservationMapper {

    fun toListResponse(reservations: List<Reservation>): List<ReservationResponse>

    fun toResponse(reservation: Reservation): ReservationResponse

}