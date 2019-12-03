package com.verycreatives.popularmovies.network

import com.verycreatives.popularmovies.models.Movie
import com.verycreatives.popularmovies.models.MoviesResponse
import io.reactivex.Single
import retrofit2.http.*

interface RestApiInterface {

    @GET("movie/top_rated")
    fun getMoviesByRate(@Query("api_key") api_key : String/*,
                        @Field("language") language : String,
                        @Field("page") page : Int,
                        @Field("region") region : String*/): Single<MoviesResponse>


    @GET("movie/popular")
    fun getMoviesByPopularity(@Query("api_key") api_key : String): Single<MoviesResponse>


    @GET("movie/{movie_id}")
    fun getMovieById(@Path("movie_id") id:Int, @Query("api_key") api_key: String ): Single<Movie>


}