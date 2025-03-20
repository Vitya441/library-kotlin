package by.modsen.libraryapp.mapper

import by.modsen.libraryapp.dto.request.BookRequest
import by.modsen.libraryapp.dto.response.BookResponse
import by.modsen.libraryapp.entity.Book
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface BookMapper {

    fun toEntity(bookRequest: BookRequest): Book

    fun toResponse(book : Book): BookResponse
}