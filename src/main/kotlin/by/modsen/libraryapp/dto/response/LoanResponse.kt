package by.modsen.libraryapp.dto.response

import java.time.LocalDate

data class LoanResponse(
    var id: Long?,
    val book: BookResponse,
    val reader: ReaderResponse,
    val loanDate: LocalDate,
    val dueDate: LocalDate,
    var isReturned: Boolean
)