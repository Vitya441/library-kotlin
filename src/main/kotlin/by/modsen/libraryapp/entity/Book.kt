package by.modsen.libraryapp.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class Book(

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var genre: String,

    @Column(nullable = false)
    var author: String,

    @Column(nullable = false)
    var totalCopies: Int,

    @Column(nullable = false)
    var availableCopies: Int

) : BaseEntity()
