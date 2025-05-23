package by.modsen.libraryapp.controller

import by.modsen.libraryapp.dto.response.ReservationResponse
import by.modsen.libraryapp.service.ReservationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Переписать в Postman URL-ы
 */
@RestController
@RequestMapping("/reservations")
class ReservationController(private val reservationService: ReservationService) {

    @PostMapping
    fun create(@RequestParam readerId: Long, @RequestParam bookId: Long): ResponseEntity<ReservationResponse> {
        return ResponseEntity.ok(reservationService.reserveBook(readerId, bookId))
    }

    @PutMapping("/{id}/confirm")
    fun confirm(@PathVariable id: Long) {
        reservationService.confirmReservation(id)
    }

    @PutMapping("/{id}/reject")
    fun reject(@PathVariable id: Long) {
        reservationService.rejectReservation(id)
    }

    @GetMapping("/readers/{readerId}")
    fun getByReaderId(@PathVariable readerId: Long): List<ReservationResponse> {
        return reservationService.getReservationsByReaderId(readerId)
    }

    @GetMapping
    fun getAll(): List<ReservationResponse> {
        return reservationService.getAllReservations();
    }

}