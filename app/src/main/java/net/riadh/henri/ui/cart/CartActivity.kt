package net.riadh.henri.ui.cart

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import net.riadh.henri.R
import net.riadh.henri.databinding.ActivityCartBinding
import net.riadh.henri.util.SharedPrefManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class CartActivity : AppCompatActivity() {

    private val disposable = CompositeDisposable()

    private lateinit var binding: ActivityCartBinding

    private val viewModel: CartViewModel by viewModel()

    private var errorSnackbar: Snackbar? = null

    private val prefs: SharedPrefManager by inject()

    private lateinit var cardNumberTxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        binding.bookList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

        binding.viewModel = viewModel
        viewModel.loadOffers()

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
