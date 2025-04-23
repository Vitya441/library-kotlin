package by.modsen.libraryapp.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDate

@Entity
class Loan(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    val book: Book,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id", nullable = false)
    val reader: Reader,

    val loanDate: LocalDate = LocalDate.now(),

    val dueDate: LocalDate,

    var isReturned: Boolean = false

) : BaseEntity()
