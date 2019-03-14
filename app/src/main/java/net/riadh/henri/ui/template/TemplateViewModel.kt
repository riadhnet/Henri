package net.riadh.henri.ui.template

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable

class TemplateViewModel : ViewModel() {

    private lateinit var subscription: Disposable

    val errorMessage: MutableLiveData<String> = MutableLiveData()


    fun initData() {
        //init subscription
    }


    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

}