package com.verycreatives.popularmovies.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.verycreatives.popularmovies.models.Movie
import com.verycreatives.popularmovies.models.MoviesResponse
import com.verycreatives.popularmovies.network.RestApiClient
import com.verycreatives.popularmovies.repository.MoviesRepository
import com.verycreatives.popularmovies.views.PopularMovies
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlin.coroutines.coroutineContext

class MoviesViewModel : ViewModel() {


    private val localRepo = MoviesRepository.instance

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean>
        get() = _error

    private val _movies = MutableLiveData<ArrayList<Movie>>()
    val movies: LiveData<ArrayList<Movie>>
        get() = _movies

    fun getMovies(sort_type: Int) {

        _isRefreshing.value = true
        when (sort_type) {

            PopularMovies.SORT_RANK -> {
                RestApiClient.retrofit.getMoviesByRate("8d61230b01928fe55a53a48a41dc839b")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : SingleObserver<MoviesResponse> {
                        var disposable: Disposable? = null
                        override fun onSubscribe(d: Disposable) {
                            disposable = d
                        }

                        override fun onSuccess(response: MoviesResponse) {
                            disposable?.dispose()
                            _movies.postValue(response.results)
                            _isRefreshing.value = false
                            _error.value = false
                        }

                        override fun onError(e: Throwable) {
                            _isRefreshing.value = false
                            Log.d("heree", "onError | Throwable received = " + e.message)
                            _error.value = true
                        }
                    })
            }
            PopularMovies.SORT_POPUL -> {
                RestApiClient.retrofit.getMoviesByPopularity("8d61230b01928fe55a53a48a41dc839b")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : SingleObserver<MoviesResponse> {
                        var disposable: Disposable? = null
                        override fun onSubscribe(d: Disposable) {
                            disposable = d
                        }

                        override fun onSuccess(response: MoviesResponse) {
                            disposable?.dispose()
                            _movies.postValue(response.results)
                            _isRefreshing.value = false
                            _error.value = false
                        }

                        override fun onError(e: Throwable) {
                            _isRefreshing.value = false
                            Log.d("heree", "onError | Throwable received = " + e.message)
                            _error.value = true
                        }
                    })
            }
            PopularMovies.SORT_FAV -> {
                _isRefreshing.value = false
                val tempList =ArrayList<Movie>()

                //add favorite movies
                localRepo.getFavMovies()?.let {
                    tempList.addAll(it)

                }

                /*RestApiClient.retrofit.getMoviesByPopularity("8d61230b01928fe55a53a48a41dc839b")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : SingleObserver<MoviesResponse> {
                        var disposable: Disposable? = null
                        override fun onSubscribe(d: Disposable) {
                            disposable = d
                        }

                        override fun onSuccess(response: MoviesResponse) {
                            disposable?.dispose()

                            response.results.forEach { m->
                                if (!exists(m,tempList)){
                                    tempList.add(m)
                                }
                            }
                            _movies.postValue(tempList)


                            _isRefreshing.value = false
                            _error.value = false
                        }

                        override fun onError(e: Throwable) {
                            _isRefreshing.value = false
                            Log.d("heree", "onError | Throwable received = " + e.message)
                            _error.value = true
                        }
                    })*/
            }
        }
    }

    private fun exists(movie: Movie, tempList: java.util.ArrayList<Movie>): Boolean {
        tempList.forEach {localMov->
            if (movie.id==localMov.id) return true
        }
        return false
    }


}