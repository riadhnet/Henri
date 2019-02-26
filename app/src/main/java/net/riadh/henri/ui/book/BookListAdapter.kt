package net.riadh.henri.ui.book

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import net.riadh.henri.R
import net.riadh.henri.databinding.ItemBookBinding
import net.riadh.henri.model.Book


class BookListAdapter : RecyclerView.Adapter<BookListAdapter.ViewHolder>() {
    private lateinit var bookList: List<Book>

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

    class ViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = BookViewModel()

        fun bind(book: Book) {
            viewModel.bind(book)
            binding.viewModel = viewModel
        }
    }
}