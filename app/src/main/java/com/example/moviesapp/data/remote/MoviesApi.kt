package com.example.moviesapp.data.remote

import com.example.moviesapp.BuildConfig
import com.example.moviesapp.data.MoviesResults
import com.example.moviesapp.data.remote.Casts.CastDetails
import com.example.moviesapp.data.remote.Casts.Casts
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val MEDIA_TYPE = "movie"
const val TIME_WINDOW = "week"

interface MoviesApi {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }
    //Get functions to fetch movies
    @GET("search/movie")
    suspend fun getMovies(
        @Query("query") query: String,
        @Query("api_key") key: String
    ): MoviesResults
    @GET("trending/${MEDIA_TYPE}/${TIME_WINDOW}")
    suspend fun getTrendingMovies(
        @Query("api_key") api_key: String = BuildConfig.API_KEY,
        @Query("media_type") media_type: String = MEDIA_TYPE,
        @Query("time_window") time_window: String = TIME_WINDOW,
    ): MoviesResults
    @GET("discover/movie")
    suspend fun getActionMovies(
        @Query("api_key") api_key: String = BuildConfig.API_KEY,
        @Query("with_genres") with_genres: String = "28"
    ): MoviesResults
    @GET("discover/movie")
    suspend fun getComedyMovies(
        @Query("api_key") api_key: String = BuildConfig.API_KEY,
        @Query("with_genres") with_genres: String = "35"
    ): MoviesResults
    @GET("discover/movie")
    suspend fun getHorrorMovies(
        @Query("api_key") api_key: String = BuildConfig.API_KEY,
        @Query("with_genres") with_genres: String = "27"
    ): MoviesResults
    @GET("discover/movie")
    suspend fun getRomanceMovies(
        @Query("api_key") api_key: String = BuildConfig.API_KEY,
        @Query("with_genres") with_genres: String = "10749"
    ): MoviesResults
    @GET("discover/movie")
    suspend fun getScifiMovies(
        @Query("api_key") api_key: String = BuildConfig.API_KEY,
        @Query("with_genres") with_genres: String = "878"
    ): MoviesResults
    @GET("movie/{movie_id}/credits")
    suspend fun getCast(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String = BuildConfig.API_KEY,
    ): Casts
    @GET("person/{person_id}")
    suspend fun getCastDetails(
        @Path("person_id") person_id: Int,
        @Query("api_key") api_key: String = BuildConfig.API_KEY
    ): CastDetails
}