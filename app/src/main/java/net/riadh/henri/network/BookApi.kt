package net.riadh.henri.network

import io.reactivex.Observable
import net.riadh.henri.model.Book
import net.riadh.henri.model.Offers
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * The interface which provides methods to get result of webservices
 */
interface BookApi {
    /**
     * Get the list of the pots from the API
     */
    @GET("/books")
    fun getBooks(): Observable<List<Book>>

    /**
     * Get the list of the pots from the API
     */
    @GET("/books/{isbn}/commercialOffers")
    fun getOffer(@Path("isbn") isbnsArray: String): Observable<List<Offers>>
}