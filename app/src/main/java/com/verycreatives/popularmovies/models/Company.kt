package com.verycreatives.popularmovies.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Company(
    @PrimaryKey
    val id: Int,
    val name: String,
    val logo_path: String?,
    val origin_country: String

) {

}
