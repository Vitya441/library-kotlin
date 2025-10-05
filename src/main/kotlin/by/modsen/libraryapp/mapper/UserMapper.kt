package by.modsen.libraryapp.mapper

import by.modsen.libraryapp.dto.response.UserResponse
import by.modsen.libraryapp.entity.User
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface UserMapper {

    fun toResponse(user: User): UserResponse
}