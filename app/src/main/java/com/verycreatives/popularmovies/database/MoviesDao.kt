package com.verycreatives.popularmovies.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.verycreatives.popularmovies.models.Movie

@Dao
abstract class MoviesDao {

    @Query("SELECT * FROM Movie")
    abstract fun getFavMovies() : List<Movie>

    @Query("SELECT * FROM Movie where id = :id")
    abstract fun getMovieById(id : Int) : Movie

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertFavorite(movie: Movie?) :Long

    @Delete
    abstract fun delete(movie : Movie?): Int



}