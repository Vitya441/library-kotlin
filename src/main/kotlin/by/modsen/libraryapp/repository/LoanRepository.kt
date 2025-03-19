package by.modsen.libraryapp.repository

import by.modsen.libraryapp.entity.Loan
import org.springframework.data.jpa.repository.JpaRepository

interface LoanRepository : JpaRepository<Loan, Long?>
