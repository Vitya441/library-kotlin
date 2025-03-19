package by.modsen.libraryapp.dto.response

class PaginatedResponse<T>(
    val content: List<T>,
    val totalPages: Int,
    val totalElements: Long,
    val currentPage: Int,
)