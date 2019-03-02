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
import net.riadh.henri.model.Offers
import net.riadh.henri.repository.BookRepositoryImpl
import net.riadh.henri.ui.cart.CartViewModel
import net.riadh.henri.util.ExceptionUtilInterface
import net.riadh.henri.util.SharedPrefManager
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CartViewModelTest {

    @get:Rule
    val schedulers = RxImmediateSchedulerRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var bookApi: BookRepositoryImpl

    @MockK
    lateinit var exceptionUtil: ExceptionUtilInterface

    @MockK
    lateinit var prefs: SharedPrefManager

    private val book1 =
        Book("cover.jpg", "isbn", 35, listOf("desc1", "desc2"), "book1")
    private val book2 =
        Book("cover.jpg", "isbn", 30, listOf("desc1", "desc2"), "book1")


    private val bookList = ArrayList<Book>(listOf(book1, book2))


    private val offer1 =
        Offer("percentage", 0, 5)

    private val offer2 =
        Offer("minus", 0, 15)

    private val offer3 =
        Offer("percentage", 0, 12)

    private val offers = Offers(listOf(offer1, offer2, offer3))


    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        every { exceptionUtil.showError(any()) } returns "Error"



        every { prefs.getBooks() } returns bookList
    }

    @Test
    fun calculateDiscount() {
        //GIVEN
        val cartViewModel = CartViewModel(bookApi, exceptionUtil, prefs)
        every { bookApi.getOffers(cartViewModel.getOfferArray()) } returns Observable.just(offers)
        //WHEN
        cartViewModel.loadOffers()
        //THEN
        Assert.assertEquals(
            "Check Books Price ",
            65,
            cartViewModel.priceDouble.toInt()
        )

        Assert.assertEquals(
            "Check best discount ",
            "15.0",
            cartViewModel.bestDiscount.toString()
        )
        Assert.assertEquals(
            "Check price after discount ",
            "50.0",
            cartViewModel.finalPriceDouble.toString()
        )
    }

    @Test
    fun showError() {
        //GIVEN
        val x = Exception()
        every { bookApi.getOffers(any()) } returns Observable.error(x)
        val cartViewModel = spyk(CartViewModel(bookApi, exceptionUtil, prefs), recordPrivateCalls = true)
        //WHEN
        cartViewModel.loadOffers()
        //THEN
        verify { cartViewModel invoke "onRetrieveOffersListError" withArguments listOf(x) }


    }

}