package by.modsen.libraryapp.dto.request

import by.modsen.libraryapp.domain.entity.Reader

data class ReaderRequest(
    val name: String
)

fun ReaderRequest.toEntity() = Reader(
    name = name
)