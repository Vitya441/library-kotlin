package by.modsen.libraryapp.service

import by.modsen.libraryapp.dto.request.LoanRequest
import by.modsen.libraryapp.dto.response.LoanResponse

interface LoanService {

    fun createLoan(loanRequest: LoanRequest): LoanResponse

    fun completeLoan(loanId: Long): LoanResponse

    fun getLoansByReaderId(readerId: Long): List<LoanResponse>

    fun getActiveLoansByReaderId(readerId: Long): List<LoanResponse>
}