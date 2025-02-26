package by.modsen.libraryapp.repository

import by.modsen.libraryapp.domain.entity.Loan
import org.springframework.data.jpa.repository.JpaRepository

interface LoanRepository : JpaRepository<Loan, Long?>
