package com.verycreatives.popularmovies.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.varunest.sparkbutton.SparkEventListener
import com.verycreatives.popularmovies.R
import com.verycreatives.popularmovies.databinding.ActivityMovieDetailsBinding
import com.verycreatives.popularmovies.viewmodels.DetailsViewModel
import kotlinx.android.synthetic.main.activity_movie_details.*
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar


class MovieDetails : AppCompatActivity() {

    companion object {
        private const val START_SWIPE_REFRESH = 50
    }

    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var model: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        model = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        binding.viewmodel = model

        val swipeRefreshLayout=findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)

        swipeRefreshLayout.setProgressViewOffset(true, START_SWIPE_REFRESH, resources.getDimension(R.dimen.swipe_refresh_offset).toInt())

        val id = intent.getIntExtra("movieId", -1)

        if (id > 0) model.getMovieById(id)

        model.movie.observe(this, Observer {
            Log.d("hereee","$it")
            it.favorite?.let { spark_button.isChecked = it }
        })

        model.error.observe(this, androidx.lifecycle.Observer { isError->
            if(isError) {
                Snackbar.make(binding.root, "Server error", Snackbar.LENGTH_LONG).show()

                scrollview.visibility= View.GONE

            }
        })

        model.isRefreshing.observe(this, Observer {it->
            swipeRefreshLayout.isRefreshing=it
        })

        swipeRefreshLayout.setOnRefreshListener {
            model.getMovieById(id)
        }



        spark_button.setEventListener(object : SparkEventListener {
            override fun onEvent(button: ImageView?, buttonState: Boolean) {
                model.onFavoriteClicked(buttonState)
            }
            override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {}
            override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {

            }
        })
    }

}

