package net.riadh.henri.ui.room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import net.riadh.henri.R
import net.riadh.henri.databinding.ActivityRoomBinding
import net.riadh.henri.ui.login.LoginActivity.Companion.room_key
import net.riadh.henri.util.SharedPrefManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class RoomActivity : AppCompatActivity() {

    private val disposable = CompositeDisposable()

    private lateinit var binding: ActivityRoomBinding

    private val viewModel: RoomViewModel by viewModel()

    private var errorSnackbar: Snackbar? = null

    private val prefs: SharedPrefManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_template)

        val roomKey = intent.getStringExtra(room_key)
        if (roomKey == null) {
            finish()
        }

        binding.viewModel = viewModel
        viewModel.initData(roomKey)


    }


}
