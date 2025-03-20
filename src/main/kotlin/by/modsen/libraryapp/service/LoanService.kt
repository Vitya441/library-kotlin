package by.modsen.libraryapp.service

import by.modsen.libraryapp.entity.Loan

interface LoanService {

    // TODO: Нас сколько дней пользователь может взять книгу (отдолжить) ?
    // Следить за этим будет не читатель, а сотрудник на стороне библиотеки
    fun borrowBook(readerId: Long, bookId: Long, days: Long = 14): Loan

    fun returnBook(loanId: Long)
}