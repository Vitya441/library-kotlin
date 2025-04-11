package by.modsen.libraryapp.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

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
)
