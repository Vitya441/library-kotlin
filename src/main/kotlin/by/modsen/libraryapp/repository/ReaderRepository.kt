package by.modsen.libraryapp.repository

import by.modsen.libraryapp.entity.Reader
import org.springframework.data.jpa.repository.JpaRepository

interface ReaderRepository : JpaRepository<Reader, Long?>
