package by.modsen.libraryapp.service

import by.modsen.libraryapp.dto.request.ReaderRequest
import by.modsen.libraryapp.dto.response.PaginatedResponse
import by.modsen.libraryapp.dto.response.ReaderResponse

interface ReaderService {

    fun save(readerRequest: ReaderRequest): ReaderResponse

    fun getById(id: Long): ReaderResponse

    fun getAll(): List<ReaderResponse>

    fun getPage(page: Int, size: Int): PaginatedResponse<ReaderResponse>

    fun deleteById(id: Long)
}