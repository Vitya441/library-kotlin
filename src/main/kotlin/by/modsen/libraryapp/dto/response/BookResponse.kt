package by.modsen.libraryapp.dto.response

import by.modsen.libraryapp.domain.entity.Book

data class BookResponse(
    val id: Long?,
    val title: String,
    val genre: String,
    val author: String,
    var totalCopies: Int,
    var availableCopies: Int,
)

fun Book.toResponse() = BookResponse(
    id = id,
    title = title,
    genre = genre,
    author = author,
    totalCopies = totalCopies,
    availableCopies = availableCopies
)