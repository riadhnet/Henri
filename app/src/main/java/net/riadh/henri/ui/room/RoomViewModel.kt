package net.riadh.henri.ui.room

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.disposables.Disposable

class RoomViewModel : ViewModel() {

    private lateinit var subscription: Disposable

    val errorMessage: MutableLiveData<String> = MutableLiveData()

    val database = FirebaseDatabase.getInstance()
    val myRef = database.reference

    fun initData(roomKey: String) {
        //init subscription
    }


    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

}