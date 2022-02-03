package com.example.moviesapp.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movies_favorites")
data class MoviesFav(
    @Json(name = "title") val title: String,
    @PrimaryKey(autoGenerate = true)
    @Json(name = "id") val id: Int,
    @Json(name = "release_date") val release_date: String,
    @Json(name = "overview") val overview: String,
    @Json(name = "vote_average") val vote_average: String,
    @Json(name = "poster_path") val poster_path: String,
    @Json(name = "original_language") val original_language: String,
    var isFavorite: Boolean
) : Parcelable {
}

