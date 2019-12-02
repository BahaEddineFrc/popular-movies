package com.verycreatives.popularmovies.database

import androidx.room.*
import com.verycreatives.popularmovies.models.Movie

@Dao
abstract class MoviesDao {

    @Query("SELECT * FROM Movie")
    abstract fun getFavMovies() : List<Movie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertFavorite(movie: Movie?): Long

    @Delete
    abstract fun delete(movie : Movie?): Int



}