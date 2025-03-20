package by.modsen.libraryapp.mapper

import by.modsen.libraryapp.dto.request.ReaderRequest
import by.modsen.libraryapp.dto.response.ReaderResponse
import by.modsen.libraryapp.entity.Reader
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface ReaderMapper {

    fun toEntity(readerRequest: ReaderRequest): Reader

    fun toResponse(reader: Reader): ReaderResponse
}