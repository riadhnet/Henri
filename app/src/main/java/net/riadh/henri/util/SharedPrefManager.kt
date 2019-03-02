package net.riadh.henri.util

import android.content.Context
import com.afollestad.rxkprefs.Pref
import com.afollestad.rxkprefs.rxkPrefs
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import net.riadh.henri.app.CART_PREF
import net.riadh.henri.model.Book
import java.io.IOException

//The use of rxkPrefs was made just for fun and test instead of android sharedPref
open class SharedPrefManager(context: Context) {

    private val myPrefs = rxkPrefs(context)

    private val myString: Pref<String> = myPrefs.string(CART_PREF)

    private val jsonAdapter = Moshi.Builder().build()
        .adapter<List<Book>>(Types.newParameterizedType(List::class.java, Book::class.javaObjectType))

    open fun addBook(book: Book) {
        var books: ArrayList<Book>
        try {
            books = jsonAdapter.fromJson(myString.get()) as ArrayList<Book>
        } catch (e: IOException) {
            books = ArrayList()
        }

        books.add(book)
        myString.set(jsonAdapter.toJson(books))
    }

    open fun getBooks(): ArrayList<Book> {
        return try {
            jsonAdapter.fromJson(myString.get()) as ArrayList<Book>
        } catch (e: IOException) {
            ArrayList()
        }

    }


}