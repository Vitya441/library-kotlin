package by.modsen.libraryapp.service

import by.modsen.libraryapp.dto.request.BookRequest
import by.modsen.libraryapp.dto.request.BookPatchRequest
import by.modsen.libraryapp.dto.response.BookResponse
import by.modsen.libraryapp.dto.response.PaginatedResponse

interface BookService {

    fun save(bookRequest: BookRequest): BookResponse

    fun getById(id: Long): BookResponse

    fun getAll(): List<BookResponse>

    fun getAllPaged(page: Int, size: Int): PaginatedResponse<BookResponse>

    fun updateBookById(id: Long, updateRequest: BookRequest): BookResponse

    fun patchBookById(id: Long, patchRequest: BookPatchRequest): BookResponse

    fun deleteById(id: Long)
}