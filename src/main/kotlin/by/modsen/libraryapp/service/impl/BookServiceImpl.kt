package by.modsen.libraryapp.service.impl

import by.modsen.libraryapp.dto.request.BookRequest
import by.modsen.libraryapp.dto.request.BookPatchRequest
import by.modsen.libraryapp.dto.response.BookResponse
import by.modsen.libraryapp.dto.response.PaginatedResponse
import by.modsen.libraryapp.entity.Book
import by.modsen.libraryapp.exception.NotFoundException
import by.modsen.libraryapp.mapper.BookMapper
import by.modsen.libraryapp.repository.BookRepository
import by.modsen.libraryapp.service.BookService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookServiceImpl(
    private val bookRepository: BookRepository,
    private val bookMapper: BookMapper
) : BookService {

    override fun save(bookRequest: BookRequest): BookResponse {
        val bookEntity = bookMapper.toEntity(bookRequest)
        bookRepository.save(bookEntity)

        return bookMapper.toResponse(bookEntity)
    }

    override fun getById(id: Long): BookResponse {
        val bookEntity = getBookByIdOrThrow(id)
        return bookMapper.toResponse(bookEntity)
    }

    override fun getAll(): List<BookResponse> {
        val books = bookRepository.findAll()
        return books.map { bookMapper.toResponse(it) }
    }

    override fun getAllPaged(page: Int, size: Int): PaginatedResponse<BookResponse> {
        val pageable: Pageable = PageRequest.of(page, size)
        val booksPage = bookRepository.findAll(pageable)

        return PaginatedResponse(
            content = booksPage.content.map { bookMapper.toResponse(it) },
            totalPages = booksPage.totalPages,
            totalElements = booksPage.totalElements,
            currentPage = booksPage.number
        )
    }

    override fun updateBookById(id: Long, updateRequest: BookRequest): BookResponse {
        val book = getBookByIdOrThrow(id);
        bookMapper.updateEntityFromDto(updateRequest, book)
        bookRepository.save(book)

        return bookMapper.toResponse(book)
    }

    override fun patchBookById(id: Long, patchRequest: BookPatchRequest): BookResponse {
        val book = getBookByIdOrThrow(id)
        bookMapper.patchEntityFromDto(patchRequest, book)
        bookRepository.save(book)

        return bookMapper.toResponse(book)
    }

    override fun deleteById(id: Long) {
        if (!bookRepository.existsById(id)) {
            throw NotFoundException("Book with id = $id not found")
        }
        bookRepository.deleteById(id)
    }

    private fun getBookByIdOrThrow(bookId: Long): Book {
        return bookRepository
            .findById(bookId)
            .orElseThrow { NotFoundException("Book with id = $bookId not found") }
    }
}