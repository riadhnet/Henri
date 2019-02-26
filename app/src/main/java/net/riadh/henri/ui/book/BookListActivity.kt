package net.riadh.henri.ui.book

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import net.riadh.henri.R
import net.riadh.henri.databinding.ActivityBookListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookListActivity : AppCompatActivity() {

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
