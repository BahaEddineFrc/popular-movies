package com.verycreatives.popularmovies.network

import com.verycreatives.popularmovies.models.Movie
import com.verycreatives.popularmovies.models.MoviesResponse
import io.reactivex.Single
import retrofit2.http.*

interface RestApiInterface {

    @Headers("api_key: 8d61230b01928fe55a53a48a41dc839b")
    @GET("movie/top_rated")
    fun getMoviesByRate(@Query("api_key") api_key : String/*,
                        @Field("language") language : String,
                        @Field("page") page : Int,
                        @Field("region") region : String*/): Single<MoviesResponse>

    @Headers("api_key: 8d61230b01928fe55a53a48a41dc839b")
    @GET("movie/popular")
    fun getMoviesByPopularity(@Query("api_key") api_key : String): Single<MoviesResponse>

}