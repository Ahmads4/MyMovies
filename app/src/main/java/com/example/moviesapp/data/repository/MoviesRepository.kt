package com.example.moviesapp.data.repository

import com.example.moviesapp.BuildConfig
import com.example.moviesapp.data.MoviesResults
import com.example.moviesapp.data.remote.Casts.CastDetails
import com.example.moviesapp.data.remote.Casts.Casts
import com.example.moviesapp.data.remote.MEDIA_TYPE
import com.example.moviesapp.data.remote.MoviesApi
import com.example.moviesapp.data.remote.TIME_WINDOW
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
//We use Inject because I own this class, unlike the Retrofit and MoviesApi class
class MoviesRepository @Inject constructor(private val moviesApi: MoviesApi) {
    //Functions to be called in the viewmodel
    suspend fun getSearchResults(query: String): List<MoviesResults.Movies> {
        return moviesApi.getMovies(query, BuildConfig.API_KEY).results
    }

    suspend fun getTrendingMovies(): List<MoviesResults.Movies> {
        return moviesApi.getTrendingMovies(BuildConfig.API_KEY, MEDIA_TYPE, TIME_WINDOW).results
    }

    suspend fun getActionMovies(): List<MoviesResults.Movies> {
        return moviesApi.getActionMovies(BuildConfig.API_KEY, "28").results
    }

    suspend fun getComedyMovies(): List<MoviesResults.Movies> {
        return moviesApi.getComedyMovies(BuildConfig.API_KEY, "35").results
    }

    suspend fun getHorrorMovies(): List<MoviesResults.Movies> {
        return moviesApi.getHorrorMovies(BuildConfig.API_KEY, "27").results
    }

    suspend fun getRomanceMovies(): List<MoviesResults.Movies> {
        return moviesApi.getRomanceMovies(BuildConfig.API_KEY, "10749").results
    }

    suspend fun getScifiMovies(): List<MoviesResults.Movies> {
        return moviesApi.getScifiMovies(BuildConfig.API_KEY, "878").results
    }

    suspend fun getCast(movieId: Int): List<Casts.Crew> {
        return moviesApi.getCast(movieId, BuildConfig.API_KEY).cast
    }

    suspend fun getCastDetails(personId: Int): CastDetails {
        return moviesApi.getCastDetails(personId, BuildConfig.API_KEY)
    }
}