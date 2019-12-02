package com.verycreatives.popularmovies.repository

import com.verycreatives.popularmovies.database.MoviesDao
import com.verycreatives.popularmovies.models.Movie


class MoviesRepository(private val moviesDao: MoviesDao) {

    fun createOrUpdate(movie: Movie) : Long{
        return moviesDao.createOrUpdate(movie)
    }

    fun insertAllAndSynchronize(pots: List<Movie>) {
        return moviesDao.insertAllAndSynchronize(pots)
    }

    fun getMovies(page: Int) = moviesDao.getMovies()

    companion object {
        val instance : MoviesRepository= MoviesRepository(MyApplication.database.moviesDao())
    }

}