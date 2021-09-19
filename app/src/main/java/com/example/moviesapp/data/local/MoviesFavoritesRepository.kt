package com.example.moviesapp.data.local

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.properties.Delegates

class MoviesFavoritesRepository @Inject constructor(private val moviesDao: MovieDao) {


    var id by Delegates.notNull<Int>()

    //Add movie to favorites
    suspend fun insertFavorite(favorite: MoviesFav) {
        moviesDao.favorite(favorite)
    }

    //Remove movie from favorites
    suspend fun deleteFavorite(favorite: MoviesFav) {
        moviesDao.delete(favorite)
    }

    //Get boolean value to assign it to the checkbox in the details fragment
    suspend fun getBool(id: Int): Boolean? {
        return moviesDao.getBooleanValue(id)
    }

    //List of favorited ids
    val favIdList: Flow<List<Int>> = moviesDao.isItemChecked()

    //Lit of favorited movies
    val favoriteMovies: Flow<List<MoviesFav>> = moviesDao.getFavorites()

}
