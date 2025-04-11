package by.modsen.libraryapp.mapper

import by.modsen.libraryapp.dto.request.BookPatchRequest
import by.modsen.libraryapp.dto.request.BookRequest
import by.modsen.libraryapp.dto.response.BookResponse
import by.modsen.libraryapp.entity.Book
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface BookMapper {

    fun toEntity(bookRequest: BookRequest): Book

    fun toResponse(book : Book): BookResponse

    fun updateEntityFromDto(updateRequest: BookRequest, @MappingTarget book: Book)

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun patchEntityFromDto(patchRequest: BookPatchRequest, @MappingTarget book: Book)
}