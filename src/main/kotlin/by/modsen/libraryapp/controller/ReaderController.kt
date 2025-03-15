package by.modsen.libraryapp.controller

import by.modsen.libraryapp.dto.request.ReaderRequest
import by.modsen.libraryapp.dto.response.PaginatedResponse
import by.modsen.libraryapp.dto.response.ReaderResponse
import by.modsen.libraryapp.service.ReaderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/readers")
class ReaderController(private val readerService: ReaderService) {

    @PostMapping
    fun save(readerRequest: ReaderRequest): ResponseEntity<ReaderResponse> {
        val readerResponse = readerService.save(readerRequest)
        return ResponseEntity(readerResponse, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<ReaderResponse> {
        val readerResponse = readerService.getById(id)
        return ResponseEntity(readerResponse, HttpStatus.OK)
    }

    @GetMapping
    fun getAll(): ResponseEntity<List<ReaderResponse>> {
        val listResponse = readerService.getAll()
        return ResponseEntity.ok(listResponse)
    }

    @GetMapping("/page")
    fun getPage(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): PaginatedResponse<ReaderResponse> {
        return readerService.getPage(page, size)
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Unit> {
        readerService.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}