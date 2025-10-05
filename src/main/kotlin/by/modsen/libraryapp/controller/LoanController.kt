package by.modsen.libraryapp.controller

import by.modsen.libraryapp.dto.request.LoanRequest
import by.modsen.libraryapp.dto.response.LoanResponse
import by.modsen.libraryapp.service.LoanService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/loans")
class LoanController(private val loanService: LoanService) {

    @PostMapping
    fun create(@RequestBody loanRequest: LoanRequest): ResponseEntity<LoanResponse> {
        return ResponseEntity.ok(loanService.createLoan(loanRequest))
    }

    @PutMapping("/{loanId}/complete")
    fun complete(@PathVariable loanId: Long): ResponseEntity<LoanResponse> {
        return ResponseEntity.ok(loanService.completeLoan(loanId))
    }

    @GetMapping("/readers/{readerId}")
    fun getByReaderId(@PathVariable readerId: Long): ResponseEntity<List<LoanResponse>> {
        return ResponseEntity.ok(loanService.getLoansByReaderId(readerId))
    }

    @GetMapping("/readers/{readerId}/active")
    fun getActiveByReaderId(@PathVariable readerId: Long): ResponseEntity<List<LoanResponse>> {
        return ResponseEntity.ok(loanService.getActiveLoansByReaderId(readerId))
    }
}