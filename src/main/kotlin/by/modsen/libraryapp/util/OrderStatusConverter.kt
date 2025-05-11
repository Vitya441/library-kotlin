package by.modsen.libraryapp.util

import by.modsen.libraryapp.enumeration.OrderStatus
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class OrderStatusConverter : AttributeConverter<OrderStatus, String> {

    override fun convertToDatabaseColumn(attribute: OrderStatus): String {
        return attribute.label
    }

    override fun convertToEntityAttribute(dbData: String): OrderStatus {
        return OrderStatus.fromLabel(dbData)
    }
}