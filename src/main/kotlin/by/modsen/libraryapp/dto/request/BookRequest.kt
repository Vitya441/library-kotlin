package by.modsen.libraryapp.dto.request

data class BookRequest(
    val title: String,
    val genre: String,
    val author: String,
    val totalCopies: Int,
    val availableCopies: Int
)