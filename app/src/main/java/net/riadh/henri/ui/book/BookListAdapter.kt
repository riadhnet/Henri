package net.riadh.henri.ui.book

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import net.riadh.henri.R
import net.riadh.henri.databinding.ItemBookBinding
import net.riadh.henri.model.Book
import net.riadh.henri.ui.book.listener.BookClickListener


open class BookListAdapter : RecyclerView.Adapter<BookListAdapter.ViewHolder>() {
    private lateinit var bookList: List<Book>
    lateinit var bookClickListener: BookClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListAdapter.ViewHolder {
        val binding: ItemBookBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_book, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookListAdapter.ViewHolder, position: Int) {
        holder.bind(bookList[position])
    }

    override fun getItemCount(): Int {
        return if (::bookList.isInitialized) bookList.size else 0
    }

    fun updateBookList(bookList: List<Book>) {
        this.bookList = bookList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root),
        BookClickListener {
        override fun onAddToCart(book: Book) {
            bookClickListener.onAddToCart(book)
        }

        override fun onReadSummaryClickListener(book: Book) {
            bookClickListener.onReadSummaryClickListener(book)
        }

        private val viewModel = BookViewModel(bookClickListener)
        fun bind(book: Book) {
            viewModel.bind(book)
            binding.viewModel = viewModel

        }

    }
}