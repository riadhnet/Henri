package net.riadh.henri.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import net.riadh.henri.R
import net.riadh.henri.databinding.ActivityLoginBinding
import net.riadh.henri.ui.room.RoomActivity
import net.riadh.henri.util.SharedPrefManager
import net.riadh.henri.util.extension.openActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : AppCompatActivity() {

    companion object {
        public const val room_key = "room_key";
    }

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


        disposable.add(viewModel.roomClick.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { e -> e.printStackTrace() }
            .subscribe { room ->
                openActivity(RoomActivity::class.java, room_key, room.uuid!!)
                Toast.makeText(this, room.roomName, Toast.LENGTH_LONG).show()
            }
        )

    }


}
