package net.riadh.henri.network

import io.reactivex.Observable
import net.riadh.henri.model.Book
import retrofit2.http.GET

/**
 * The interface which provides methods to get result of webservices
 */
interface BookApi {
    /**
     * Get the list of the pots from the API
     */
    @GET("/books")
    fun getBooks(): Observable<List<Book>>
}