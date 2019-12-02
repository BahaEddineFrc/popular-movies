package com.verycreatives.popularmovies.repository

import com.verycreatives.popularmovies.database.MoviesDao
import com.verycreatives.popularmovies.models.Movie


class MoviesRepository(private val moviesDao: MoviesDao) {



    fun getFavMovies() = moviesDao.getFavMovies()

    fun insertFavorite(movie: Movie?) : Long {
        return moviesDao.insertFavorite(movie)
    }

    fun delete(movie: Movie?) : Int {
        return moviesDao.delete(movie)
    }

    companion object {
        val instance : MoviesRepository= MoviesRepository(MyApplication.database.moviesDao())
    }

}