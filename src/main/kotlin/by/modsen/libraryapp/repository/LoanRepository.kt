package by.modsen.libraryapp.repository

import by.modsen.libraryapp.entity.Loan
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LoanRepository : JpaRepository<Loan, Long?>
