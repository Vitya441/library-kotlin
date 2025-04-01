package by.modsen.libraryapp.service

import by.modsen.libraryapp.dto.response.LoanResponse

interface LoanService {

    fun borrowBook(readerId: Long, bookId: Long, days: Long = 14): LoanResponse

    fun returnBook(loanId: Long): LoanResponse

    fun getAllByReaderId(readerId: Long): List<LoanResponse>

    fun getUnreturnedBooksByReaderId(readerId: Long): List<LoanResponse>
}