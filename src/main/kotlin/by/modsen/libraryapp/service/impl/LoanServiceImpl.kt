package by.modsen.libraryapp.service.impl

import by.modsen.libraryapp.dto.request.LoanRequest
import by.modsen.libraryapp.dto.response.LoanResponse
import by.modsen.libraryapp.entity.Loan
import by.modsen.libraryapp.exception.NoAvailableBooksException
import by.modsen.libraryapp.exception.NotFoundException
import by.modsen.libraryapp.mapper.LoanMapper
import by.modsen.libraryapp.repository.BookRepository
import by.modsen.libraryapp.repository.LoanRepository
import by.modsen.libraryapp.repository.UserRepository
import by.modsen.libraryapp.service.LoanService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class LoanServiceImpl(
    private val loanRepository: LoanRepository,
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val loanMapper: LoanMapper,
) : LoanService {

    /**
     * Будет вызывать библиотекарь в библиотеке, когда к нему придет пользователь?
     */
    @Transactional
    override fun createLoan(loanRequest: LoanRequest): LoanResponse {
        val readerId = loanRequest.readerId
        val bookId = loanRequest.bookId

        val reader = userRepository
            .findById(readerId)
            .orElseThrow { NotFoundException("Reader with id = $readerId not found") }

        val book = bookRepository
            .findById(bookId)
            .orElseThrow { NotFoundException("Book with id = $bookId not found") }

        if (book.availableCopies <= 0) {
            throw NoAvailableBooksException("No available copies for book with id = $bookId")
        }

        // Высчитываем дату возврата
        val dueDate = LocalDate.now().plusDays(loanRequest.days)

        val loan = Loan(book = book, reader = reader, dueDate = dueDate)
        loanRepository.save(loan)

        book.availableCopies -= 1
        bookRepository.save(book)

        return loanMapper.toResponse(loan)
    }

    /**
     * Будет вызывать библиотекарь,
     * чтобы по ID договора обновить кол-во книг и статус договора
     */
    @Transactional
    override fun completeLoan(loanId: Long): LoanResponse {
        val loan = loanRepository
            .findById(loanId)
            .orElseThrow { NotFoundException("Loan with id = $loanId not found") }

        val bookId = loan.book.id!!
        val book = bookRepository.findById(bookId).get()
        book.availableCopies += 1
        loan.isReturned = true

        loanRepository.save(loan)
        bookRepository.save(book)

        return loanMapper.toResponse(loan)
    }

    override fun getLoansByReaderId(readerId: Long): List<LoanResponse> {
        if (!userRepository.existsById(readerId)) {
            throw NotFoundException("Reader with id = $readerId not found")
        }
        val loans = loanRepository.findAllByReaderId(readerId)

        return loanMapper.toListResponse(loans)
    }

    override fun getActiveLoansByReaderId(readerId: Long): List<LoanResponse> {
        if (!userRepository.existsById(readerId)) {
            throw NotFoundException("Reader with id = $readerId not found")
        }
        val loans = loanRepository.findAllByReaderIdAndIsReturnedFalse(readerId)

        return loanMapper.toListResponse(loans)
    }
}
