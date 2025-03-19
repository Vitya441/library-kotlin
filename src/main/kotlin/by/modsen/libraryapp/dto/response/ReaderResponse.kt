package by.modsen.libraryapp.dto.response

import by.modsen.libraryapp.entity.Reader

data class ReaderResponse(
    val id: Long?,
    val name: String,
)

fun Reader.toResponse() = ReaderResponse(
    id = id,
    name = name
)