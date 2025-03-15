package by.modsen.libraryapp.service

import by.modsen.libraryapp.dto.request.BookRequest
import by.modsen.libraryapp.dto.response.BookResponse
import by.modsen.libraryapp.dto.response.PaginatedResponse

interface BookService {

    fun save(bookRequest: BookRequest): BookResponse

    fun getById(id: Long): BookResponse

    fun getAll(): List<BookResponse>

    fun getAllPaged(page: Int, size: Int): PaginatedResponse<BookResponse>

    fun deleteById(id: Long)
}