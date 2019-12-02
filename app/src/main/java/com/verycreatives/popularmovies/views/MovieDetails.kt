package com.verycreatives.popularmovies.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.verycreatives.popularmovies.R
import com.verycreatives.popularmovies.databinding.ActivityMovieDetailsBinding
import com.verycreatives.popularmovies.viewmodels.DetailsViewModel
import com.verycreatives.popularmovies.viewmodels.MoviesViewModel

class MovieDetails : AppCompatActivity() {


    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var model: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        model = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        binding.viewmodel = model

        val id = intent.getIntExtra("movieId", -1)
        if(id>0) model.getMovieById(id)


    }
}
