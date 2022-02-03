package com.example.moviesapp.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    //Add movie to favorites
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun favorite(fav: MoviesFav)
    //Remove movie from favorites
    @Delete
    suspend fun delete(fav: MoviesFav)
    //Get boolean value to assign it to the checkbox in the details fragment
    @Query("SELECT isFavorite from movies_favorites where id = :id ")
    suspend fun getBooleanValue(id: Int): Boolean?
    //Get the id of the favorited movie
    @Query("SELECT id from movies_favorites where isFavorite = 1")
    fun isItemChecked(): Flow<List<Int>>
    //Get the favorited movies
    @Query("SELECT * FROM movies_favorites where isFavorite = 1 ")
    fun getFavorites(): Flow<List<MoviesFav>>
}