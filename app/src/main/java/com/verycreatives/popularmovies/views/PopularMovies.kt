package com.verycreatives.popularmovies.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.verycreatives.popularmovies.R
import com.verycreatives.popularmovies.adapters.MoviesAdapter
import com.verycreatives.popularmovies.databinding.ActivityPopularMoviesBinding
import com.verycreatives.popularmovies.viewmodels.DetailsViewModel
import com.verycreatives.popularmovies.viewmodels.MoviesViewModel
import android.widget.Toast



class PopularMovies : AppCompatActivity() {

    private lateinit var binding : ActivityPopularMoviesBinding
    private lateinit var  model : MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_movies)


        val swipeRefreshLayout=findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)

        swipeRefreshLayout.setProgressViewOffset(true, START_SWIPE_REFRESH, resources.getDimension(R.dimen.swipe_refresh_offset).toInt())


        binding = ActivityPopularMoviesBinding.inflate(layoutInflater)
        model = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        binding.viewmodel=model

        //model.getMovies(SORT_POPUL)

        model.error.observe(this, androidx.lifecycle.Observer { isError->
            if(isError)
                Snackbar.make(binding.root,"Server error", Snackbar.LENGTH_LONG).show()
        })

        model.isRefreshing.observe(this, Observer {it->
            swipeRefreshLayout.isRefreshing=it
        })


        swipeRefreshLayout.setOnRefreshListener {
            model.getMovies(SORT_POPUL)
        }

        setUpRecyclerView()

    }

    companion object {
        private const val START_SWIPE_REFRESH = 50
        const val SORT_POPUL = 1
        const val SORT_RANK = 2
        const val SORT_FAV = 3
    }

    private fun setUpRecyclerView() {

        val recyclerView : RecyclerView = findViewById(R.id.movies_rv)
        val emptyPlaceholder : LinearLayout = findViewById(R.id.empty_placeholder)

        recyclerView.layoutManager = GridLayoutManager(this,2)

        val adapter = MoviesAdapter(this, emptyPlaceholder) {movie->
            intent = Intent(this@PopularMovies, MovieDetails::class.java)
            val bundle = bundleOf("movie" to movie)
            startActivity(intent,bundle)
        }

        recyclerView.adapter = adapter

        binding.viewmodel?.getMovies(SORT_POPUL)

        model.movies.observe(this, Observer { movies ->
            adapter.setMovies(movies)
            Log.d("heree","model.movies.observed")
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.movies_sort_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        //noinspection SimplifiableIfStatement

        if (id == R.id.popular_sort) {
            model.getMovies(SORT_POPUL)
            return true
        }
        if (id == R.id.top_rated_sort) {
            model.getMovies(SORT_RANK)
            return true
        }
        if (id == R.id.fav_sort) {
            model.getMovies(SORT_FAV)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}
