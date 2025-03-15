package by.modsen.libraryapp.service.impl

import by.modsen.libraryapp.dto.request.BookRequest
import by.modsen.libraryapp.dto.response.BookResponse
import by.modsen.libraryapp.dto.response.PaginatedResponse
import by.modsen.libraryapp.dto.request.toEntity
import by.modsen.libraryapp.dto.response.toResponse
import by.modsen.libraryapp.domain.entity.Book
import by.modsen.libraryapp.repository.BookRepository
import by.modsen.libraryapp.service.BookService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookServiceImpl(private val bookRepository: BookRepository) : BookService {

    override fun save(bookRequest: BookRequest): BookResponse {
        val bookEntity = bookRequest.toEntity()
        bookRepository.save(bookEntity)

        return bookEntity.toResponse()
    }

    override fun getById(id: Long): BookResponse {
        val bookEntity = getBookByIdOrThrow(id)

        return bookEntity.toResponse()
    }

    override fun getAll(): List<BookResponse> {
        val books = bookRepository.findAll()
        return books.map { it.toResponse() }
    }

    override fun getAllPaged(page: Int, size: Int): PaginatedResponse<BookResponse> {
        val pageable: Pageable = PageRequest.of(page, size)
        val booksPage = bookRepository.findAll(pageable)

        return PaginatedResponse(
            content = booksPage.content.map { it.toResponse() },
            totalPages = booksPage.totalPages,
            totalElements = booksPage.totalElements,
            currentPage = booksPage.number
        )
    }

    override fun deleteById(id: Long) {
        if (!bookRepository.existsById(id)) {
            throw RuntimeException("Book not found with id: $id")
        }
        bookRepository.deleteById(id)
    }

    /**
     * Для пере-использования из других методов
     */
    private fun getBookByIdOrThrow(bookId: Long): Book {
        return bookRepository
            .findById(bookId)
            .orElseThrow { RuntimeException("Book not found with id: $bookId") }
    }
}