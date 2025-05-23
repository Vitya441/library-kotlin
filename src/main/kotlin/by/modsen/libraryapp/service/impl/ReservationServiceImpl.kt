package by.modsen.libraryapp.service.impl

import by.modsen.libraryapp.dto.response.ReservationResponse
import by.modsen.libraryapp.entity.Loan
import by.modsen.libraryapp.entity.Reservation
import by.modsen.libraryapp.enumeration.OrderStatus
import by.modsen.libraryapp.exception.NotFoundException
import by.modsen.libraryapp.mapper.ReservationMapper
import by.modsen.libraryapp.repository.BookRepository
import by.modsen.libraryapp.repository.LoanRepository
import by.modsen.libraryapp.repository.ReaderRepository
import by.modsen.libraryapp.repository.ReservationRepository
import by.modsen.libraryapp.service.ReservationService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

/**                             TODO:
 * Подумать над следующим, зарезервировать, значит уменьшить доступное кол-во на
 * еденицу, у меня в коде пока что не изменяется кол-во доступных книг при резервировании
 */

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
            .orElseThrow { NotFoundException("Reader with id = $readerId not found") }

        val book = bookRepository
            .findById(bookId)
            .orElseThrow { NotFoundException("Book with id = $bookId not found") }

        val reservation = Reservation(
            book = book,
            reader = reader,
            reservationDate = LocalDate.now(),
            status = OrderStatus.PENDING,
        )

        reservationRepository.save(reservation)
        return reservationMapper.toResponse(reservation)
    }

    /**                TODO: возможно нет
     * Возможно ручное одобрение или отклонение заявки убрать,
     * а просто проверять долги пользователя в приложении, и если
     * их нет, то позволять делать бронирование
     */
    @Transactional
    override fun rejectReservation(reservationId: Long) {
        val reservation = reservationRepository
            .findByIdAndStatus(reservationId, OrderStatus.PENDING)
            .orElseThrow { NotFoundException("Бронирование не найдено") }

        reservation.status = OrderStatus.CANCELLED
        reservationRepository.save(reservation)
    }

    /**
     * Когда бронь (reservation) одобряют -> создаётся задолженность (loan)
     * и количество доступных экземпляров книг уменьшается.
     */
    @Transactional
    override fun confirmReservation(reservationId: Long) {
        val reservation = reservationRepository
            .findByIdAndStatus(reservationId, OrderStatus.PENDING)
            .orElseThrow { NotFoundException("Бронирование не найдено") }

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
            .orElseThrow { NotFoundException("Book with id = $bookId not found") }

        book.availableCopies -= 1
        bookRepository.save(book)

        loanRepository.save(loan)
    }

    override fun getReservationsByReaderId(readerId: Long): List<ReservationResponse> {
        val reservations: List<Reservation> = reservationRepository.findByReaderId(readerId)
        return reservationMapper.toListResponse(reservations)
    }

    override fun getAllReservations(): List<ReservationResponse> {
        val entities: List<Reservation> = reservationRepository.findAll()
        return reservationMapper.toListResponse(entities);
    }
}
