package by.modsen.libraryapp.service.impl

import by.modsen.libraryapp.entity.Loan
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
) : LoanService {


    /**
     * TODO: Пока что некрасивый метод, но бизнес-логика такая
     */
    @Transactional
    override fun borrowBook(readerId: Long, bookId: Long, days: Long): Loan {
        val reader = readerRepository
            .findById(readerId)
            .orElseThrow { RuntimeException("Reader with id = $readerId not found") }

        val book = bookRepository
            .findById(bookId)
            .orElseThrow { RuntimeException("Book with id = $bookId not found") }

        if (book.availableCopies <= 0) {
            throw RuntimeException("No available copies for book with id = $bookId")
        }

        // Высчитываем дату возврата
        val dueDate = LocalDate.now().plusDays(days)

        val loan = Loan(book = book, reader = reader, dueDate = dueDate)
        loanRepository.save(loan)

        book.availableCopies -= 1
        bookRepository.save(book)

        return loan
    }

    override fun returnBook(loanId: Long) {
        TODO("Not yet implemented")
    }
}