package by.modsen.libraryapp.controller

import by.modsen.libraryapp.dto.response.LoanResponse
import by.modsen.libraryapp.service.LoanService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Пока не знаю кто по логике приложения будет использовать этот метод:
 * Читатель или сотрудник библиотеки ?
 */
@RestController
@RequestMapping("/loans")
class LoanController(private val loanService: LoanService) {

    @PostMapping("/borrow")
    fun borrowBook(
        @RequestParam readerId: Long,
        @RequestParam bookId: Long,
        @RequestParam(defaultValue = "14") days: Long
    ): ResponseEntity<LoanResponse> {
        return ResponseEntity.ok(loanService.borrowBook(readerId, bookId, days))
    }

    @PutMapping("/{loanId}/return")
    fun returnBook(@PathVariable loanId: Long): ResponseEntity<LoanResponse> {
        return ResponseEntity.ok(loanService.returnBook(loanId))
    }

    @GetMapping
    fun getAllByReaderId(@RequestParam readerId: Long): ResponseEntity<List<LoanResponse>> {
        return ResponseEntity.ok(loanService.getAllByReaderId(readerId))
    }

    @GetMapping("/borrowed")
    fun getBorrowedBooksByReaderId(@RequestParam readerId: Long): ResponseEntity<List<LoanResponse>> {
        return ResponseEntity.ok(loanService.getUnreturnedBooksByReaderId(readerId))
    }

}