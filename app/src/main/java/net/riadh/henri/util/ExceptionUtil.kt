package net.riadh.henri.util

import android.content.Context
import net.riadh.henri.R
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.ConnectException


open class ExceptionUtil(private val context: Context) : ExceptionUtilInterface {

    override fun showError(throwable: Throwable): String {

        if (throwable is HttpException) {
            when (throwable.code()) {
                404 -> return context.getString(R.string.not_found)
                401 -> return context.getString(R.string.not_authorized)
                else -> handleApiError(throwable.response().errorBody()!!)
            }
        } else if (throwable is ConnectException) {
            return context.getString(R.string.check_internet)
        } else if (throwable is SecurityException) {
            return context.getString(R.string.security_exception)
        }

        return context.getString(R.string.call_error)
    }


    private fun handleApiError(responseBody: ResponseBody): String {
        return responseBody.string()
    }

}