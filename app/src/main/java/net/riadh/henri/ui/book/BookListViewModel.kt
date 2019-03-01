package net.riadh.henri.ui.book

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import net.riadh.henri.model.Book
import net.riadh.henri.repository.BookRepositoryImpl
import net.riadh.henri.ui.book.listener.BookClickListener
import net.riadh.henri.util.ExceptionUtilInterface

class BookListViewModel(
    private val bookApi: BookRepositoryImpl,
    private val exceptionUtil: ExceptionUtilInterface
) : ViewModel(), BookClickListener {


    private lateinit var subscription: Disposable
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    val bookListAdapter: BookListAdapter = BookListAdapter()

    val errorClickListener = View.OnClickListener { loadBooks() }

    val readSummaryClicked: PublishSubject<Book> = PublishSubject.create()

    val addToCartClicked: PublishSubject<Book> = PublishSubject.create()

    fun loadBooks() {

        subscription = bookApi.getBooks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveBookListStart() }
            .doOnTerminate { onRetrieveBookListFinish() }
            .subscribe(
                { result -> onRetrieveBookListSuccess(result) },
                { t ->
                    onRetrieveBookListError(t)
                }
            )
        bookListAdapter.bookClickListener = this
    }


    override fun onReadSummaryClickListener(book: Book) {
        readSummaryClicked.onNext(book)
    }

    override fun onAddToCart(book: Book) {
        addToCartClicked.onNext(book)
    }

    private fun onRetrieveBookListStart() {
        errorMessage.value = null
        loadingVisibility.value = View.VISIBLE
    }

    private fun onRetrieveBookListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveBookListSuccess(result: List<Book>) {
        bookListAdapter.updateBookList(result)
    }

    private fun onRetrieveBookListError(error: Throwable) {
        errorMessage.value = exceptionUtil.showError(error)
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

}