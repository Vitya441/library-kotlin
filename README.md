# Kotlin Library API
## Reader Controller
- POST `/readers`- создать читателя
- GET `/readers/{id}` - получить читателя по ID
- GET `/readers` - получить всех читателей
- GET `/readers/page` - получить всхе читателей с пагинацией
  - Params:
    - page (default = 0)
    - size (default = 10)
- PUT `/readers/{id}` - изменить читателя по ID
- PATCH `/readers/{id}` - частично изменить читателя по ID
- DELETE `/readers/{id}` - удалить читателя по ID

## Book Controller
- POST `/books` - создать книгу
- GET `/books/{id}` - получить книгу по ID
- GET `/books` - получить все книги
- GET `/books/page` - получить все книги c пагинацией
  - Params:
    - page (default = 0)
    - size (default = 10)
- PUT `/books/{id}` - изменить книгу по ID 
- PATCH `/books/{id}` - частично изменить книгу по ID
- DELETE `/books/{id}` - удалить книгу по ID

## Loan Controller
- POST `/loans` - перевести определенную книгу (по ID) в состоянии взятых (создать запись об этом)
  - Params:
    - readerId,
    - bookId
    - days (default = 14)

- PUT `/loans/{id}/complete` - пометить текущий договор/долг как закрытый (погашенный)
  - Params:
    - id - ID договора/долга

- GET `/loans/readers/{readerId}` - получить всю историю долгов определённого читателя

- GET `/loans/readers/{readerId}/active` - получить текущие долги определённого читателя

## Reservation Controller
- POST `/reservations` - создать запрос на бронирование книги
  - Params: 
    - readerId
    - bookId
- PUT `/reservations/{id}/confirm` - одобрить бронирование книги для читателя
- PUT `/reservations/{id}/reject` - отклонить бронирование книги для читателя
- GET `/reservations/readers/{readerId}` - получить все предварительные заказы читателя по его ID
- GET `/reservations` - получить предварительные заказы всех читателей
