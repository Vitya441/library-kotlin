package by.modsen.libraryapp.dto.request

data class BookRequest(
    val title: String,
    val genre: String,
    val author: String,
    var totalCopies: Int,
    var availableCopies: Int
)