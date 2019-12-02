package com.verycreatives.popularmovies.database

import androidx.room.*
import com.verycreatives.popularmovies.models.Movie

@Dao
abstract class MoviesDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun createOrUpdate(movie: Movie) : Long //use return var to test its success


    public fun insertAllAndSynchronize(movies: List<Movie>){
        val  ids = ArrayList<Int>()
        movies.forEach { m->
            ids.add(m.id)
        }
        delete(ids)
        insertAll(movies)
    }

    @Query("Delete FROM Movie where id Not in (:ids) ")
    abstract fun delete(ids: ArrayList<Int>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(movies: List<Movie>) : List<Long>

    @Query("SELECT * FROM Movie")
    abstract fun getMovies() : List<Movie>

}