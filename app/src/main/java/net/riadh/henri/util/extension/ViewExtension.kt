package net.riadh.henri.util.extension

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

fun View.getParentActivity(): AppCompatActivity? {
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

fun <T> Context.openActivity(it: Class<T>, bundleKey: String, bundle: Bundle) {
    var intent = Intent(this, it)
    intent.putExtra(bundleKey, bundle)
    startActivity(intent)
}


fun <T> Context.openActivity(it: Class<T>, key: String, value: String) {
    var intent = Intent(this, it)
    intent.putExtra(key, value)
    startActivity(intent)
}
