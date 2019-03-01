package net.riadh.henri.ui.book

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import net.riadh.henri.R
import net.riadh.henri.databinding.ActivityBookListBinding
import net.riadh.henri.model.Book
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookListActivity : AppCompatActivity() {
    private val disposable = CompositeDisposable()

    private lateinit var binding: ActivityBookListBinding

    private val viewModel: BookListViewModel by viewModel()

    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_list)
        binding.bookList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

        binding.viewModel = viewModel
        viewModel.loadBooks()

        disposable.add(viewModel.itemClicked.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { book -> showSummary(book) })

    }

    private fun showSummary(book: Book) {
        MaterialDialog(this).show {
            title(text = book.title)
            listItems (items = book.synopsis)
            positiveButton(android.R.string.ok)
        }
    }

    private fun showError(errorMessage: String) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }
}
