package net.riadh.henri.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import net.riadh.henri.R
import net.riadh.henri.databinding.ActivityLoginBinding
import net.riadh.henri.util.SharedPrefManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : AppCompatActivity() {

    private val disposable = CompositeDisposable()

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModel()

    private var errorSnackbar: Snackbar? = null

    private val prefs: SharedPrefManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)


        binding.viewModel = viewModel
        viewModel.initData()

    }


}
