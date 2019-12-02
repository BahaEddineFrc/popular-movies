package com.verycreatives.popularmovies.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Country (
    @PrimaryKey
    val iso_3166_1: String, //country code
    val name: String
){

}
