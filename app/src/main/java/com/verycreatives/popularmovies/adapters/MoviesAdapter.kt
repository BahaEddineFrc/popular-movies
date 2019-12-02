package com.verycreatives.popularmovies.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.verycreatives.popularmovies.R
import com.verycreatives.popularmovies.databinding.MovieItemBinding
import com.verycreatives.popularmovies.models.Movie


class MoviesAdapter(private val context: Context, private var emptyView: View? = null,
                    private var callback :(Movie)->Unit) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {


    private var movies: List<Movie>? = null

    private var recyclerView: RecyclerView? = null


    fun setMovies(movies: List<Movie>?) {
        this.movies = movies

        if (movies == null || movies.isEmpty()) emptyView!!.visibility = View.VISIBLE
        else emptyView!!.visibility = View.GONE

        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val movieItemBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.movie_item,
            parent,
            false
        ) as MovieItemBinding
        return ViewHolder(movieItemBinding)
    }

    override fun getItemCount(): Int {
        return if (movies == null) 0 else movies!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies!![position]
        holder.bind(movie)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = null

        super.onDetachedFromRecyclerView(recyclerView)
    }

    inner class ViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind( mov: Movie) {

            binding.cardLayout.setOnClickListener {
                callback.invoke(mov)
            }
            binding.movie  = mov
            binding.executePendingBindings()

        }
    }






}
