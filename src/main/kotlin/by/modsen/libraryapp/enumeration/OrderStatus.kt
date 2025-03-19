package by.modsen.libraryapp.enumeration

enum class OrderStatus(val code: Int) {
    PENDING(0),
    CONFIRMED(1),
    CANCELLED(2);

    companion object {
        fun fromCode(code: Int): OrderStatus {
            return entries.find { it.code == code }
                ?: throw IllegalArgumentException(
                    "Failed to convert code '$code' to an Enum value for InboxStatus. " +
                            "Please check the database values or possible changes to the Enum definition."
                )
        }
    }
}