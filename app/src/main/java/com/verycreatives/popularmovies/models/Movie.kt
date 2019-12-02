package com.verycreatives.popularmovies.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

@Entity
data class Movie(
    @PrimaryKey val id: Int,
    val title: String,
    val favorite: Boolean?,
    val video: Boolean,
    val adult: Boolean,
    val backdrop_path: String?,
    //val belongs_to_collection: Any?
    //val budget: Int
    val genres_ids: ArrayList<Int>,
    //val homepage: String?
    //val imdb_id: String?
    val original_language: String,
    val original_title: String,
    val overview: String?,
    val popularity: Double,
    val poster_path: String?,
    //val production_companies: ArrayList<Company>
    //val production_countries: ArrayList<Country>
    val release_date: String,
    //val revenue: Int
    //val spoken_languages: ArrayList<Languages>
    //val status: String
    val vote_average: Double,//val tagline: String?
    val vote_count: Int,
    val runtime: Int?,
    val tagline: String?
) {

}