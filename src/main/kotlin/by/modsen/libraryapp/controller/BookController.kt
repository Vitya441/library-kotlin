package by.modsen.libraryapp.controller

import by.modsen.libraryapp.dto.request.BookPatchRequest
import by.modsen.libraryapp.dto.request.BookRequest
import by.modsen.libraryapp.dto.response.BookResponse
import by.modsen.libraryapp.dto.response.PaginatedResponse
import by.modsen.libraryapp.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/books")
class BookController(private val bookService: BookService) {

    @PostMapping
    fun save(@RequestBody bookRequest: BookRequest): ResponseEntity<BookResponse> {
        return ResponseEntity(bookService.save(bookRequest), HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<BookResponse> {
        return ResponseEntity.ok(bookService.getById(id))
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<BookResponse>> {
        return ResponseEntity.ok(bookService.getAll())
    }

    @GetMapping("/page")
    fun getAllPaged(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): PaginatedResponse<BookResponse> {
        return bookService.getAllPaged(page, size)
    }

    @PutMapping("/{id}")
    fun updateById(@PathVariable id: Long, @RequestBody updateRequest: BookRequest): ResponseEntity<BookResponse> {
        return ResponseEntity.ok(bookService.updateBookById(id, updateRequest))
    }

    @PatchMapping("/{id}")
    fun patchById(@PathVariable id: Long, @RequestBody patchRequest: BookPatchRequest): ResponseEntity<BookResponse> {
        return ResponseEntity.ok(bookService.patchBookById(id, patchRequest))
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Unit> {
        bookService.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}