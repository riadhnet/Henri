package net.riadh.henri.ui.book

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.riadh.henri.model.Book
import net.riadh.henri.ui.book.listener.BookClickListener
import net.riadh.henri.util.getFormattedPrice

class BookViewModel(private val bookClickListener: BookClickListener) : ViewModel() {
    private val bookTitle = MutableLiveData<String>()
    private val bookCover = MutableLiveData<String>()
    private val bookIsbn = MutableLiveData<String>()
    private val bookPrice = MutableLiveData<String>()
    private lateinit var mBook: Book

    fun bind(book: Book) {
        this.mBook = book
        bookTitle.value = book.title
        bookCover.value = book.cover
        bookIsbn.value = book.isbn
        bookPrice.value = getFormattedPrice(book.price)
    }

    fun getBookTitle(): MutableLiveData<String> {
        return bookTitle
    }

    fun getBookCover(): MutableLiveData<String> {
        return bookCover
    }

    fun getBookIsbn(): MutableLiveData<String> {
        return bookIsbn
    }

    fun getBookPrice(): MutableLiveData<String> {
        return bookPrice
    }

    fun onReadSummaryClick() {
        bookClickListener.onReadSummaryClickListener(mBook)
    }

    fun addToCartClick() {
        bookClickListener.onAddToCart(mBook)
    }


}

