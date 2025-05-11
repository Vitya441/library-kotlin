package by.modsen.libraryapp.dto.request

data class LoanRequest(
    val readerId: Long,
    val bookId: Long,
    val days: Long = 14
)
