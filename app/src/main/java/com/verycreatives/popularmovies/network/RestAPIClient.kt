package com.verycreatives.popularmovies.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RestApiClient {

    const val BASE_URL = "https://api.themoviedb.org/3/"

    var getClient: Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    var retrofit: RestApiInterface = getClient.create(RestApiInterface::class.java)
}