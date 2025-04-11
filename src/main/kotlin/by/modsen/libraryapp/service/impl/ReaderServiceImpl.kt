package by.modsen.libraryapp.service.impl

import by.modsen.libraryapp.dto.request.ReaderPatchRequest
import by.modsen.libraryapp.dto.request.ReaderRequest
import by.modsen.libraryapp.dto.response.PaginatedResponse
import by.modsen.libraryapp.dto.response.ReaderResponse
import by.modsen.libraryapp.entity.Reader
import by.modsen.libraryapp.exception.NotFoundException
import by.modsen.libraryapp.mapper.ReaderMapper
import by.modsen.libraryapp.repository.ReaderRepository
import by.modsen.libraryapp.service.ReaderService
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class ReaderServiceImpl(
    private val readerRepository: ReaderRepository,
    private val readerMapper: ReaderMapper,
) : ReaderService {

    override fun save(readerRequest: ReaderRequest): ReaderResponse {
        val readerEntity = readerMapper.toEntity(readerRequest)
        readerRepository.save(readerEntity)

        return readerMapper.toResponse(readerEntity)
    }

    override fun getById(id: Long): ReaderResponse {
        val readerEntity = getReaderByIdOrThrow(id)
        return readerMapper.toResponse(readerEntity)
    }

    override fun getAll(): List<ReaderResponse> {
        val readerList = readerRepository.findAll()
        return readerList.map { readerMapper.toResponse(it) }
    }

    override fun getPage(page: Int, size: Int): PaginatedResponse<ReaderResponse> {
        val pageable = PageRequest.of(page, size)
        val readersPage = readerRepository.findAll(pageable)

        return PaginatedResponse(
            content = readersPage.content.map { readerMapper.toResponse(it) },
            totalPages = readersPage.totalPages,
            totalElements = readersPage.totalElements,
            currentPage = readersPage.number
        )
    }

    override fun updateReaderById(id: Long, updateRequest: ReaderRequest): ReaderResponse {
        val reader = getReaderByIdOrThrow(id);
        readerMapper.updateEntityFromDto(updateRequest, reader)
        readerRepository.save(reader)

        return readerMapper.toResponse(reader)
    }

    override fun patchReaderById(id: Long, patchRequest: ReaderPatchRequest): ReaderResponse {
        val reader = getReaderByIdOrThrow(id);
        readerMapper.patchEntityFromDto(patchRequest, reader)
        readerRepository.save(reader)

        return readerMapper.toResponse(reader)
    }

    override fun deleteById(id: Long) {
        if (!readerRepository.existsById(id)) {
            throw NotFoundException("Reader with id = $id not found")
        }
        readerRepository.deleteById(id)
    }

    private fun getReaderByIdOrThrow(id: Long): Reader {
        return readerRepository
            .findById(id)
            .orElseThrow { NotFoundException("Reader with id = $id not found") }
    }
}
