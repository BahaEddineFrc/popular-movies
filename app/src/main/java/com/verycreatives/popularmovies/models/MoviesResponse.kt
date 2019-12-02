package com.verycreatives.popularmovies.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoviesResponse(
    @PrimaryKey
    val page : Int,
    val total_results : Int,
    val total_pages : Int,
    val results : ArrayList<Movie>
) {

}