package com.verycreatives.popularmovies.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.verycreatives.popularmovies.R

class Splash : AppCompatActivity() {

    private val SPLASH_TIME_OUT:Long = 6000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this, Login::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }

}
