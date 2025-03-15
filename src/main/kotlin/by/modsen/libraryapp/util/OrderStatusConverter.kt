package by.modsen.libraryapp.util

import by.modsen.libraryapp.domain.enumeration.OrderStatus
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class OrderStatusConverter : AttributeConverter<OrderStatus, Int> {

    override fun convertToDatabaseColumn(attribute: OrderStatus): Int {
        return attribute.code
    }

    override fun convertToEntityAttribute(dbData: Int): OrderStatus {
        return OrderStatus.fromCode(dbData)
    }
}