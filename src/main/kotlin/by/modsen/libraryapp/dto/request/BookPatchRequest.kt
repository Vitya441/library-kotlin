package by.modsen.libraryapp.dto.request

data class BookPatchRequest(
    val title: String? = null,
    val genre: String? = null,
    val author: String? = null,
    val totalCopies: Int? = null,
    val availableCopies: Int? = null,
)