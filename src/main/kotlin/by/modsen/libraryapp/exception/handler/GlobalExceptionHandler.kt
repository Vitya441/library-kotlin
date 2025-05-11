package by.modsen.libraryapp.exception.handler

import by.modsen.libraryapp.dto.response.ErrorResponse
import by.modsen.libraryapp.exception.NoAvailableBooksException
import by.modsen.libraryapp.exception.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.message!!)
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(errorResponse)
    }

    @ExceptionHandler(NoAvailableBooksException::class)
    fun noAvailableBooksException(ex: NoAvailableBooksException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(HttpStatus.CONFLICT.value(), ex.message!!)
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(errorResponse)
    }

}