package net.riadh.henri

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.Observable
import net.riadh.henri.model.Book
import net.riadh.henri.model.Offer
import net.riadh.henri.repository.BookRepositoryImpl
import net.riadh.henri.ui.book.BookListViewModel
import net.riadh.henri.util.ExceptionUtilInterface
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BookListViewModelTest {

    @get:Rule
    val schedulers = RxImmediateSchedulerRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var bookApi: BookRepositoryImpl

    @MockK
    lateinit var exceptionUtil: ExceptionUtilInterface

    private val book1 =
        Book("cover.jpg", "isbn", 35, listOf("desc1", "desc2"), "book1")
    private val book2 =
        Book("cover.jpg", "isbn", 30, listOf("desc1", "desc2"), "book1")
    private val book3 =
        Book("cover.jpg", "isbn", 30, listOf("desc1", "desc2"), "book1")
    private val book4 =
        Book("cover.jpg", "isbn", 29, listOf("desc1", "desc2"), "book1")

    private val bookList = listOf(book1, book2, book3, book4)


    private val offer1 =
        Offer("percentage", 0, 5)

    private val offer2 =
        Offer("minus", 0, 15)

    private val offer3 =
        Offer("percentage", 0, 12)

    private val offers = listOf(offer1, offer2, offer3)


    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        every { exceptionUtil.showError(any()) } returns "Error"

        every { bookApi.getBooks() } returns Observable.just(bookList)
    }

    @Test
    fun showDataFromApi() {
        //GIVEN
        val bookListViewModel = BookListViewModel(bookApi, exceptionUtil)
        //WHEN
        bookListViewModel.loadBooks()
        //THEN
        Assert.assertEquals(
            "Check that adapter has correct number of rows ",
            bookList.size,
            bookListViewModel.bookListAdapter.itemCount
        )
    }

    @Test
    fun showError() {
        //GIVEN
        val x = Exception()
        every { bookApi.getBooks() } returns Observable.error(x)
        val bookListViewModel = spyk(BookListViewModel(bookApi, exceptionUtil), recordPrivateCalls = true)
        //WHEN
        bookListViewModel.loadBooks()
        //THEN
        verify { bookListViewModel invoke "onRetrieveBookListError" withArguments listOf(x) }


    }

}