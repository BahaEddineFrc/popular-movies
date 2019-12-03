package com.verycreatives.popularmovies.repository
import com.verycreatives.popularmovies.MyApplication
import com.verycreatives.popularmovies.database.MoviesDao
import com.verycreatives.popularmovies.models.Movie


class MoviesRepository(private val moviesDao: MoviesDao) {


    fun getFavMovies() = moviesDao.getFavMovies()

    fun getMovieById(id: Int) = moviesDao.getMovieById(id) //liveData {emit(moviesDao.getMovieById(id)) }

    fun insertFavorite(movie: Movie?) : Long =
        moviesDao.insertFavorite(movie)


    fun delete(movie: Movie?) : Int = //= liveData<Int>{}
         moviesDao.delete(movie)


    companion object {
        val instance: MoviesRepository = MoviesRepository(MyApplication.database.moviesDao())
    }
}

