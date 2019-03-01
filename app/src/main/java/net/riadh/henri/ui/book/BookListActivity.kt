package net.riadh.henri.ui.book

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
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


class BookListActivity : AppCompatActivity(), View.OnClickListener {


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

        disposable.add(
            viewModel.readSummaryClicked.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { book -> showSummary(book) })

        disposable.add(viewModel.addToCartClicked.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { e -> e.printStackTrace() }
            .subscribe({ book -> addToCart(book) }, { throwable -> throwable.printStackTrace() })
        )


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_cart, menu)

        for (i in 0 until menu.size()) {
            val item = menu.getItem(i)
            if (item.itemId == R.id.action_cart) {
                item.actionView?.setOnClickListener(this)
            }
        }

        return true
    }

    override fun onClick(p0: View?) {
        //open cart
        Toast.makeText(this, "cart", Toast.LENGTH_SHORT).show()
    }


    private fun showSummary(book: Book) {
        MaterialDialog(this).show {
            title(text = book.title)
            listItems(items = book.synopsis)
            positiveButton(android.R.string.ok)
        }
    }

    private fun addToCart(book: Book) {
        Toast.makeText(this, "fff", Toast.LENGTH_SHORT).show()
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
