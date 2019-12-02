package com.verycreatives.popularmovies.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Languages(
    @PrimaryKey
    val iso_639_1: String, //Language code
    val name: String
) {

}