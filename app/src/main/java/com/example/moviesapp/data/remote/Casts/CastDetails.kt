package com.example.moviesapp.data.remote.Casts

import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class CastDetails(
    @PrimaryKey(autoGenerate = true)
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "biography") val biography: String,
    @Json(name = "profile_path") val profile_path: String
) {
}