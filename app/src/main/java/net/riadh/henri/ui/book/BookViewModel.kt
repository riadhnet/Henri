package net.riadh.henri.ui.book

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.riadh.henri.model.Book

class BookViewModel : ViewModel() {
    private val bookTitle = MutableLiveData<String>()
    private val bookBody = MutableLiveData<String>()

    fun bind(book: Book) {
        bookTitle.value = book.title
        bookBody.value = book.synopsis[0]
    }

    fun getBookTitle(): MutableLiveData<String> {
        return bookTitle
    }

    fun getBookBody(): MutableLiveData<String> {
        return bookBody
    }
}