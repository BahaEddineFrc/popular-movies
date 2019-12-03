package com.verycreatives.popularmovies.viewmodels

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.*
import com.verycreatives.popularmovies.models.Movie
import com.verycreatives.popularmovies.network.RestApiClient
import com.verycreatives.popularmovies.repository.MoviesRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DetailsViewModel : ViewModel() {



    private val localRepo = MoviesRepository.instance

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean>
        get() = _error

    val movie = MutableLiveData<Movie>()


    val title = ObservableField<String>()
    val description = ObservableField<String>()
    val tagLine = ObservableField<String>()
    val imdbRate = ObservableDouble()
    var yearRelease = ObservableField<String>()
    var duration = ObservableInt(0)
    var favorite = ObservableBoolean(false)
    var pic = ObservableField<String>()


    private fun setUpDetails(movie: Movie) {
        title.set(movie.title)
        imdbRate.set(movie.vote_average)
        pic.set(movie.poster_path)
        description.set(movie.overview)
        movie.favorite?.let { favorite.set(it) }
        yearRelease.set(movie.release_date.substring(0, 4))
        movie.runtime?.let { duration.set(it) }
        movie.tagline?.let { tagLine.set(it) }
    }

    fun onFavoriteClicked(buttonState: Boolean) {

        if (!buttonState) {
            localRepo.delete(movie.value)
        } else {
            movie.value?.let {
                it.favorite=true
                Log.d("hereee", "favorite added "+localRepo.insertFavorite(movie.value))
            }
        }


    }

    fun getMovieById(id: Int) {
        /*movie = liveData {
            localRepo.getMovieById(id).map {
                setUpDetails(it)
                Log.d("hereee", "local mov != null")
                Result.success(it)
            }*/


            val m = localRepo.getMovieById(id)
            if (m!=null) {
                movie.postValue(m)
                setUpDetails(m)
            }else {
                _isRefreshing.value = true
                RestApiClient.retrofit.getMovieById(id, "8d61230b01928fe55a53a48a41dc839b")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : SingleObserver<Movie> {
                        var disposable: Disposable? = null
                        override fun onSubscribe(d: Disposable) {
                            disposable = d
                        }

                        override fun onSuccess(response: Movie) {
                            disposable?.dispose()

                            if (movie.value?.title == null || movie.value?.title!!.isEmpty())
                             {
                                movie.postValue(response)
                                setUpDetails(response)
                            }
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

        }


    }

