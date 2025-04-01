package by.modsen.libraryapp.service.impl

import by.modsen.libraryapp.dto.response.ReservationResponse
import by.modsen.libraryapp.entity.Loan
import by.modsen.libraryapp.entity.Reservation
import by.modsen.libraryapp.enumeration.OrderStatus
import by.modsen.libraryapp.mapper.ReservationMapper
import by.modsen.libraryapp.repository.BookRepository
import by.modsen.libraryapp.repository.LoanRepository
import by.modsen.libraryapp.repository.ReaderRepository
import by.modsen.libraryapp.repository.ReservationRepository
import by.modsen.libraryapp.service.ReservationService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class ReservationServiceImpl(
    private val reservationRepository: ReservationRepository,
    private val bookRepository: BookRepository,
    private val loanRepository: LoanRepository,
    private val readerRepository: ReaderRepository,
    private val reservationMapper: ReservationMapper,
) : ReservationService {

    @Transactional
    override fun reserveBook(readerId: Long, bookId: Long): ReservationResponse {
        val reader = readerRepository
            .findById(readerId)
            .orElseThrow { RuntimeException("Reader with id = $readerId not found") }

        val book = bookRepository
            .findById(bookId)
            .orElseThrow { RuntimeException("Book with id = $bookId not found") }

        val isBookLoaned = loanRepository.existsByBookAndIsReturnedFalse(book)
        if (isBookLoaned) {
            throw RuntimeException("Book has already been issued")
        }

        val reservation = Reservation(
            book = book,
            reader = reader,
            reservationDate = LocalDate.now(),
            status = OrderStatus.PENDING,
        )

        reservationRepository.save(reservation)
        return reservationMapper.toResponse(reservation)
    }

    @Transactional
    override fun rejectReservation(reservationId: Long) {
        val reservation = reservationRepository
            .findById(reservationId)
            .orElseThrow { RuntimeException("Бронирование не найдено") }

        reservation.status = OrderStatus.CANCELLED
        reservationRepository.save(reservation)
    }

    @Transactional
    override fun confirmReservation(reservationId: Long) {
        val reservation = reservationRepository
            .findById(reservationId)
            .orElseThrow { RuntimeException("Бронирование не найдено") }

        reservation.status = OrderStatus.CONFIRMED
        reservationRepository.save(reservation)

        val loan = Loan(
            book = reservation.book,
            reader = reservation.reader,
            dueDate = LocalDate.now().plusDays(14),
        )

        val bookId = reservation.book.id ?: throw RuntimeException("Book ID is null")

        val book = bookRepository
            .findById(bookId)
            .orElseThrow { RuntimeException("Book with id = $bookId not found") }

        book.availableCopies -= 1
        bookRepository.save(book)

        loanRepository.save(loan)
    }

    override fun getReservationsByReaderId(readerId: Long): List<ReservationResponse> {
        val listResponse = reservationMapper.toListResponse(reservationRepository.findByReaderId(readerId))
        return listResponse
    }
}