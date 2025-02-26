package by.modsen.libraryapp.repository

import by.modsen.libraryapp.domain.entity.Reader
import org.springframework.data.jpa.repository.JpaRepository

interface ReaderRepository : JpaRepository<Reader, Long?>
