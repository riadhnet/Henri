package net.riadh.henri.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.riadh.henri.R
import net.riadh.henri.ui.book.BookListActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //TODO if logged in open home else open login
        val intent = Intent(this, BookListActivity::class.java)
        startActivity(intent)
        finish()
    }
}
