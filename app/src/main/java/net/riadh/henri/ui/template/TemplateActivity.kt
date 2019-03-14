package net.riadh.henri.ui.template

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import net.riadh.henri.R
import net.riadh.henri.databinding.ActivityTemplateBinding
import net.riadh.henri.util.SharedPrefManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class TemplateActivity : AppCompatActivity() {

    private val disposable = CompositeDisposable()

    private lateinit var binding: ActivityTemplateBinding

    private val viewModel: TemplateViewModel by viewModel()

    private var errorSnackbar: Snackbar? = null

    private val prefs: SharedPrefManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_template)


        binding.viewModel = viewModel
        viewModel.initData()

    }


}
