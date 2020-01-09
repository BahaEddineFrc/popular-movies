package com.verycreatives.popularmovies.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.ImageButton
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.transition.TransitionInflater
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.verycreatives.popularmovies.R
import com.verycreatives.popularmovies.databinding.ActivityLoginBinding
import com.verycreatives.popularmovies.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlin.math.hypot
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.github.ybq.android.spinkit.sprite.Sprite
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import com.github.ybq.android.spinkit.SpinKitView


class Login : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    private lateinit var model: LoginViewModel

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)


        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        model = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.viewmodel = model

        supportActionBar?.hide()

        val refreshSpinner = findViewById<SpinKitView>(R.id.spin_kit)


        model.msg.observe(this, androidx.lifecycle.Observer { msg->
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        })

        model.isRefreshing.observe(this, Observer {it->
            if(it) refreshSpinner.visibility=View.VISIBLE
            else refreshSpinner.visibility=View.GONE
        })


        model.connectedUser.observe(this, Observer {
            startActivity(Intent(this@Login,PopularMovies::class.java))
        })


        val cardRegister = findViewById<CardView>(R.id.card_register)

        val fab = findViewById<FloatingActionButton>(R.id.register_fab)
        val croix = findViewById<ImageButton>(R.id.b_close)
        val forgotPass = findViewById<TextView>(R.id.tv_forgot_password)

        fab.setOnClickListener {
            fab.hide()
            if (Build.VERSION.SDK_INT >= 21) {

                var fadeTransition = TransitionInflater.from(this)
                    .inflateTransition(R.transition.fading)


                if (cardRegister.visibility == View.GONE)
                    showCardAnimated(cardRegister)
                else
                    hideCardAnimated(cardRegister)


            } else {
                cardRegister.visibility = View.GONE
            }
        }

        croix.setOnClickListener{
            hideCardAnimated(cardRegister)
            fab.show()
        }

        forgotPass.setOnClickListener {
            startActivity(Intent(this@Login,PopularMovies::class.java))
        }

    }

    private fun showCardAnimated(card: CardView) {

        val cx = card.width / 2
        val cy = card.height / 2

        val finalRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()

        val anim = ViewAnimationUtils.createCircularReveal(card, card.width, 0, 0f, finalRadius*2)
        card.visibility = View.VISIBLE
        anim.start()
    }

    private fun hideCardAnimated(card: CardView) {

        val cx = card.width / 2
        val cy = card.height / 2

        val finalRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()

        val anim = ViewAnimationUtils.createCircularReveal(card, cx, cy, finalRadius, 0f)
        card.visibility = View.GONE
        anim.start()
    }

}

