package by.modsen.libraryapp.enumeration

enum class OrderStatus(val label: String) {
    PENDING("pending"),
    CONFIRMED("confirmed"),
    CANCELLED("cancelled");

    companion object {
        fun fromLabel(label: String): OrderStatus {
            return entries.find { it.label == label }
                ?: throw IllegalArgumentException(
                    "Failed to convert code '$label' to an Enum value for InboxStatus. " +
                            "Please check the database values or possible changes to the Enum definition."
                )
        }
    }
}