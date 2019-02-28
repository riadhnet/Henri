package net.riadh.henri.ui.book

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.riadh.henri.model.Book

class BookViewModel : ViewModel() {
    private val bookTitle = MutableLiveData<String>()
    private val bookCover = MutableLiveData<String>()

    fun bind(book: Book) {
        bookTitle.value = book.title
        bookCover.value = book.cover
    }

    fun getBookTitle(): MutableLiveData<String> {
        return bookTitle
    }

    fun getBookCover(): MutableLiveData<String> {
        return bookCover
    }
}