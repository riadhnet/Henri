package net.riadh.henri.ui.cart

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.riadh.henri.model.Book
import net.riadh.henri.repository.BookRepositoryImpl
import net.riadh.henri.util.ExceptionUtilInterface
import net.riadh.henri.util.SharedPrefManager

class CartViewModel(
    private val bookApi: BookRepositoryImpl,
    private val exceptionUtil: ExceptionUtilInterface,
    private val prefs: SharedPrefManager
) : ViewModel() {

    private lateinit var subscription: Disposable
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    val errorClickListener = View.OnClickListener { loadOffers() }

    val discount: MutableLiveData<String> = MutableLiveData()
    val price: MutableLiveData<String> = MutableLiveData()
    val finalPrice: MutableLiveData<String> = MutableLiveData()

    private val books = prefs.getBooks()

    fun loadOffers() {

        price.value = getInitialPrice().toString()

        subscription = bookApi.getBooks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveOffersListStart() }
            .doOnTerminate { onRetrieveOffersListFinish() }
            .subscribe(
                { result -> onRetrieveOffersListSuccess(result) },
                { t ->
                    onRetrieveOffersListError(t)
                }
            )
    }

    private fun getInitialPrice(): Int {
        var price = 0
        books.forEach {
            price += it.price
        }
        return price
    }

    fun clearCart() {
        prefs.clearCart()
    }


    private fun onRetrieveOffersListStart() {
        errorMessage.value = null
        loadingVisibility.value = View.VISIBLE
    }

    private fun onRetrieveOffersListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveOffersListSuccess(result: List<Book>) {
        // bookListAdapter.updateBookList(result)
    }

    private fun onRetrieveOffersListError(error: Throwable) {
        errorMessage.value = exceptionUtil.showError(error)
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

}