package net.riadh.henri.ui.login

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import net.riadh.henri.model.RoomLogin

class LoginViewModel : ViewModel() {

    private lateinit var subscription: Disposable

    val userName = MutableLiveData<String>()
    val roomName = MutableLiveData<String>()
    val roomPassword = MutableLiveData<String>()

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()


    val roomClick: PublishSubject<RoomLogin> = PublishSubject.create()

    fun initData() {
        //init subscription
    }


    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    fun login() {
        if (validateInput()) {
            roomClick.onNext(
                RoomLogin(
                    userName.value.toString(),
                    roomName.value.toString(),
                    roomPassword.value.toString()
                )
            )
        }
    }

    private fun validateInput(): Boolean {
        return !TextUtils.isEmpty(userName.value) && !TextUtils.isEmpty(roomName.value) && !TextUtils.isEmpty(
            roomPassword.value
        )
    }

}