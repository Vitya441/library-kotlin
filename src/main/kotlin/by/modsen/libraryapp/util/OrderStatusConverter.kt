package by.modsen.libraryapp.util

import by.modsen.libraryapp.domain.enumeration.OrderStatus
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

//TODO: Необязательная аннотация, но в ней есть параметр autoApply (default = false)
@Converter
class OrderStatusConverter : AttributeConverter<OrderStatus, Int> {

    override fun convertToDatabaseColumn(attribute: OrderStatus): Int {
        return attribute.code
    }

    override fun convertToEntityAttribute(dbData: Int): OrderStatus {
        return OrderStatus.fromCode(dbData)
    }
}