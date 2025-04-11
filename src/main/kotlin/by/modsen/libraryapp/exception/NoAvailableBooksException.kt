package by.modsen.libraryapp.exception

/**
 * Стоит ли плодить очень много конкретных исключений, или
 * поступить как с NotFoundException, который подходит для ситуаций когда
 * любая сущность не найдена
 */
class NoAvailableBooksException(message: String) : RuntimeException(message)
