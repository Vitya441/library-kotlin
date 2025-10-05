package by.modsen.libraryapp.mapper

import by.modsen.libraryapp.dto.response.ReservationResponse
import by.modsen.libraryapp.entity.Reservation
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = [UserMapper::class, BookMapper::class])
interface ReservationMapper {

    fun toListResponse(reservations: List<Reservation>): List<ReservationResponse>

    fun toResponse(reservation: Reservation): ReservationResponse

}