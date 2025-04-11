package by.modsen.libraryapp.service.impl

import by.modsen.libraryapp.dto.response.LoanResponse
import by.modsen.libraryapp.entity.Loan
import by.modsen.libraryapp.exception.NoAvailableBooksException
import by.modsen.libraryapp.exception.NotFoundException
import by.modsen.libraryapp.mapper.LoanMapper
import by.modsen.libraryapp.repository.BookRepository
import by.modsen.libraryapp.repository.LoanRepository
import by.modsen.libraryapp.repository.ReaderRepository
import by.modsen.libraryapp.service.LoanService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class LoanServiceImpl(
    private val loanRepository: LoanRepository,
    private val bookRepository: BookRepository,
    private val readerRepository: ReaderRepository,
    private val loanMapper: LoanMapper,
) : LoanService {

    @Transactional
    override fun borrowBook(readerId: Long, bookId: Long, days: Long): LoanResponse {
        val reader = readerRepository
            .findById(readerId)
            .orElseThrow { NotFoundException("Reader with id = $readerId not found") }

        val book = bookRepository
            .findById(bookId)
            .orElseThrow { NotFoundException("Book with id = $bookId not found") }

        if (book.availableCopies <= 0) {
            throw NoAvailableBooksException("No available copies for book with id = $bookId")
        }

        // Высчитываем дату возврата
        val dueDate = LocalDate.now().plusDays(days)

        val loan = Loan(book = book, reader = reader, dueDate = dueDate)
        loanRepository.save(loan)

        book.availableCopies -= 1
        bookRepository.save(book)

        return loanMapper.toResponse(loan)
    }

    @Transactional
    override fun returnBook(loanId: Long): LoanResponse {
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

    override fun getAllByReaderId(readerId: Long): List<LoanResponse> {
        if (!readerRepository.existsById(readerId)) {
            throw NotFoundException("Reader with id = $readerId not found")
        }
        val loans = loanRepository.findAllByReaderId(readerId)

        return loanMapper.toListResponse(loans)
    }

    override fun getUnreturnedBooksByReaderId(readerId: Long): List<LoanResponse> {
        if (!readerRepository.existsById(readerId)) {
            throw NotFoundException("Reader with id = $readerId not found")
        }
        val loans = loanRepository.findAllByReaderIdAndIsReturnedFalse(readerId)

        return loanMapper.toListResponse(loans)
    }

}