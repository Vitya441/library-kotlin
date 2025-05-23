package by.modsen.libraryapp.service

import by.modsen.libraryapp.dto.request.ReaderPatchRequest
import by.modsen.libraryapp.dto.request.ReaderRequest
import by.modsen.libraryapp.dto.response.PaginatedResponse
import by.modsen.libraryapp.dto.response.ReaderResponse

interface ReaderService {

    fun save(readerRequest: ReaderRequest): ReaderResponse

    fun getById(id: Long): ReaderResponse

    fun getAll(): List<ReaderResponse>

    fun getPage(page: Int, size: Int): PaginatedResponse<ReaderResponse>

    fun updateReaderById(id: Long, updateRequest: ReaderRequest): ReaderResponse

    fun patchReaderById(id: Long, patchRequest: ReaderPatchRequest): ReaderResponse

    fun deleteById(id: Long)
}