package com.verycreatives.popularmovies.viewmodels

import android.util.Log
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.verycreatives.popularmovies.models.Movie
import com.verycreatives.popularmovies.models.MoviesResponse
import com.verycreatives.popularmovies.network.RestApiClient
import com.verycreatives.popularmovies.repository.MoviesRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.time.LocalDate
import java.util.*

class DetailsViewModel : ViewModel() {

    private val localRepo = MoviesRepository.instance

    var movie = MutableLiveData<Movie>()

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
        yearRelease.set(movie.release_date.substring(0,4))
        movie.runtime?.let { duration.set(it) }
        movie.tagline?.let { tagLine.set(it) }
        //Log.d("hereee","setUpDetails Title : ${movie.title}")
    }

    fun onFavoriteClicked(v:View){

        if (favorite.get()){
            //was favorite -> delete
            localRepo.delete(movie.value)
            Log.d("hereee","favorite deleted from database")
        }else{
            //wasn't favorite
            localRepo.insertFavorite(movie.value)
            Log.d("hereee","favorite added to database")
        }


    }

    fun getMovieById(id : Int) {
        RestApiClient.retrofit.getMovieById(id,"8d61230b01928fe55a53a48a41dc839b")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Movie> {
                var disposable: Disposable? = null
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onSuccess(response: Movie) {
                    disposable?.dispose()
                    movie.postValue(response)
                    setUpDetails(response)

                    Log.d("heree", "onSuccess getMovieById | received = " + response)
                    //repository.insertAllAndSynchronize(response.results)
                    //_isRefreshing.value = false
                    //_error.value = false
                }

                override fun onError(e: Throwable) {
                    //_isRefreshing.value = false
                    Log.d("heree", "onError | Throwable received = " + e.message)
                    //_error.value = true
                }
            })
    }

}
