package by.modsen.libraryapp.dto.response

data class BookResponse(
    val id: Long?,
    val title: String,
    val genre: String,
    val author: String,
    var totalCopies: Int,
    var availableCopies: Int,
)