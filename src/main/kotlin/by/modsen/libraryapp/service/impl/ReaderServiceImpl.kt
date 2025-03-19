package by.modsen.libraryapp.service.impl

import by.modsen.libraryapp.entity.Reader
import by.modsen.libraryapp.dto.request.ReaderRequest
import by.modsen.libraryapp.dto.request.toEntity
import by.modsen.libraryapp.dto.response.PaginatedResponse
import by.modsen.libraryapp.dto.response.ReaderResponse
import by.modsen.libraryapp.dto.response.toResponse
import by.modsen.libraryapp.repository.ReaderRepository
import by.modsen.libraryapp.service.ReaderService
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class ReaderServiceImpl(private val readerRepository: ReaderRepository) : ReaderService {

    override fun save(readerRequest: ReaderRequest): ReaderResponse {
        val readerEntity = readerRequest.toEntity()
        readerRepository.save(readerEntity)

        return readerEntity.toResponse()
    }

    override fun getById(id: Long): ReaderResponse {
        val readerEntity = getReaderByIdOrThrow(id)
        return readerEntity.toResponse()
    }

    override fun getAll(): List<ReaderResponse> {
        val readerList = readerRepository.findAll()
        return readerList.map { it.toResponse() }
    }

    override fun getPage(page: Int, size: Int): PaginatedResponse<ReaderResponse> {
        val pageable = PageRequest.of(page, size)
        val readersPage = readerRepository.findAll(pageable)

        return PaginatedResponse(
            content = readersPage.content.map { it.toResponse() },
            totalPages = readersPage.totalPages,
            totalElements = readersPage.totalElements,
            currentPage = readersPage.number
        )
    }

    override fun deleteById(id: Long) {
        if (!readerRepository.existsById(id)) {
            throw RuntimeException("Reader not found with id: $id")
        }
        readerRepository.deleteById(id)
    }

    private fun getReaderByIdOrThrow(id: Long): Reader {
        return readerRepository
            .findById(id)
            .orElseThrow { RuntimeException("Reader with id: $id not found") }
    }
}