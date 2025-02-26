package by.modsen.libraryapp.repository

import by.modsen.libraryapp.domain.entity.Book
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Long?>
