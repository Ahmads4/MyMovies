package com.example.moviesapp.data.remote.Casts

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Casts(
    @Json(name = "cast") val cast: List<Crew>
) : Parcelable {
    @Parcelize
    data class Crew(
        @PrimaryKey(autoGenerate = true)
        @Json(name = "id") val id: Int,
        @Json(name = "name") val name: String,
        @Json(name = "character") val character: String,
        @Json(name = "profile_path") val profile_path: String
    ) : Parcelable {}
}