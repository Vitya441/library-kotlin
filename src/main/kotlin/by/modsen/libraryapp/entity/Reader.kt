package by.modsen.libraryapp.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class Reader(

    @Column(nullable = false)
    var firstName: String,

    @Column(nullable = false)
    var lastName: String

) : BaseEntity()
