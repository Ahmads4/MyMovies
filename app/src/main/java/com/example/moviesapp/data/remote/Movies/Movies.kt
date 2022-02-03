package com.example.moviesapp.data

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviesResults(
    @Json(name = "results") val results: List<Movies>,
) : Parcelable {
    @Parcelize
    data class Movies(
        @Json(name = "title") val title: String,
        @PrimaryKey(autoGenerate = true)
        @Json(name = "id") var id: Int,
        @Json(name = "release_date") val release_date: String,
        @Json(name = "overview") val overview: String,
        @Json(name = "vote_average") val vote_average: String,
        @Json(name = "poster_path") val poster_path: String,
        @Json(name = "original_language") val original_language: String,
        var isFavorite: Boolean
    ) : Parcelable {
    }
}