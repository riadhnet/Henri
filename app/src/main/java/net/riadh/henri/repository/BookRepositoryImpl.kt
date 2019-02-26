package net.riadh.henri.repository

import io.reactivex.Observable
import net.riadh.henri.model.Book
import net.riadh.henri.network.BookApi


class BookRepositoryImpl(private val bookApi: BookApi) {
    fun getBooks(): Observable<List<Book>> {
        return bookApi.getBooks()
    }
}