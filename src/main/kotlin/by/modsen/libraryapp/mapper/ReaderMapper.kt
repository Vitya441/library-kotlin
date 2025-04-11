package by.modsen.libraryapp.mapper

import by.modsen.libraryapp.dto.request.ReaderPatchRequest
import by.modsen.libraryapp.dto.request.ReaderRequest
import by.modsen.libraryapp.dto.response.ReaderResponse
import by.modsen.libraryapp.entity.Reader
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface ReaderMapper {

    fun toEntity(readerRequest: ReaderRequest): Reader

    fun toResponse(reader: Reader): ReaderResponse

    fun updateEntityFromDto(updateRequest: ReaderRequest, @MappingTarget reader: Reader)

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun patchEntityFromDto(patchRequest: ReaderPatchRequest, @MappingTarget reader: Reader)
}