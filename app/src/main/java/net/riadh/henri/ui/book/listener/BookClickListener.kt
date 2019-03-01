package net.riadh.henri.ui.book.listener


import net.riadh.henri.model.Book

interface BookClickListener {
    fun onReadSummaryClickListener(book: Book)
    fun onAddToCart(book: Book)
}
