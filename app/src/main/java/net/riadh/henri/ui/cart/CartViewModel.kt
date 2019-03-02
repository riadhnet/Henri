package net.riadh.henri.ui.cart

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import net.riadh.henri.model.Offer
import net.riadh.henri.model.OfferType
import net.riadh.henri.model.Offers
import net.riadh.henri.repository.BookRepositoryImpl
import net.riadh.henri.util.ExceptionUtilInterface
import net.riadh.henri.util.SharedPrefManager
import net.riadh.henri.util.getFormattedPrice

class CartViewModel(
    private val bookApi: BookRepositoryImpl,
    private val exceptionUtil: ExceptionUtilInterface,
    prefs: SharedPrefManager
) : ViewModel() {

    private lateinit var subscription: Disposable
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    val errorClickListener = View.OnClickListener { loadOffers() }

    val discount: MutableLiveData<String> = MutableLiveData()
    val price: MutableLiveData<String> = MutableLiveData()
    val finalPrice: MutableLiveData<String> = MutableLiveData()

    val clearCartClicked: PublishSubject<Int> = PublishSubject.create()

    private val books = prefs.getBooks()

    var priceDouble = getInitialPrice().toDouble()

    var bestDiscount: Double = 0.0

    var finalPriceDouble: Double = 0.0

    fun loadOffers() {

        price.value = getFormattedPrice(getInitialPrice())

        subscription = bookApi.getOffers(getOfferArray())
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

    fun getOfferArray(): String {
        val isbn = ArrayList<String>()
        books.forEach {
            isbn.add(it.isbn)
        }
        return isbn.toString().replace(" ", "").drop(1).dropLast(1)
    }

    private fun getInitialPrice(): Int {
        var price = 0
        books.forEach {
            price += it.price
        }
        return price
    }

    fun clearCart() {
        clearCartClicked.onNext(0)
    }

    private fun onRetrieveOffersListStart() {
        errorMessage.value = null
        loadingVisibility.value = View.VISIBLE
    }

    private fun onRetrieveOffersListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveOffersListSuccess(result: Offers) {
        bestDiscount = getBestDiscount(result.offers)
        finalPriceDouble = priceDouble - bestDiscount

        discount.value = getFormattedPrice(bestDiscount)
        finalPrice.value = getFormattedPrice(finalPriceDouble)
    }

    private fun getBestDiscount(offers: List<Offer>): Double {

        var minus = 0.0
        var percentage = 0.0
        var slice = 0

        offers.forEach {
            when (it.type.toUpperCase()) {
                OfferType.MINUS.name -> {
                    minus = minusValue(it.value)
                }

                OfferType.PERCENTAGE.name -> {
                    percentage = minusPercentage(it.value)
                }

                OfferType.SLICE.name -> {
                    slice = sliceValue(it.sliceValue, it.value)
                }
            }
        }

        var discountValue = minus
        if (discountValue < percentage) {
            discountValue = percentage
        }
        if (discountValue < slice) {
            discountValue = slice.toDouble()
        }
        return discountValue
    }

    private fun sliceValue(sliceValue: Int, value: Int): Int {
        return (priceDouble / sliceValue).toInt() * value
    }

    private fun minusPercentage(value: Int): Double {
        return priceDouble / 100 * value
    }

    private fun minusValue(value: Int): Double {
        return value.toDouble()
    }

    private fun onRetrieveOffersListError(error: Throwable) {
        errorMessage.value = exceptionUtil.showError(error)
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

}