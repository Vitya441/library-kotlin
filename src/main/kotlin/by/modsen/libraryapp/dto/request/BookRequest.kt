package by.modsen.libraryapp.dto.request

import by.modsen.libraryapp.domain.entity.Book

data class BookRequest(
    val title: String,
    val genre: String,
    val author: String,
    var totalCopies: Int,
    var availableCopies: Int
)

fun BookRequest.toEntity() = Book(
    title = title,
    genre = genre,
    author = author,
    totalCopies = totalCopies,
    availableCopies = availableCopies
)