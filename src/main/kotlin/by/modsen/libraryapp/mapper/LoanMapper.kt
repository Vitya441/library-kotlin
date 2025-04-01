package by.modsen.libraryapp.mapper

import by.modsen.libraryapp.dto.response.LoanResponse
import by.modsen.libraryapp.entity.Loan
import org.mapstruct.Mapper

@Mapper(componentModel = "spring", uses = [BookMapper::class, ReaderMapper::class])
interface LoanMapper {

    fun toResponse(loan: Loan): LoanResponse

    fun toListResponse(loans: List<Loan>): List<LoanResponse>
}