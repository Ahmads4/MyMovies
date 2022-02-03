package com.example.moviesapp.di

import android.app.Application
import androidx.room.Room
import com.example.moviesapp.data.local.MoviesRoomDatabase
import com.example.moviesapp.data.remote.MoviesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) //Tells Dagger that we need the methods for the entirety of the app
object AppModule {
    //These methods are never used, just instructions for Dagger
    //Method that tells Dagger how to create a Retrofit object
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(MoviesApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    //Method that tells Dagger how to create an instance of the MoviesApi
    @Provides
    @Singleton
    fun provideMoviesApi(retrofit: Retrofit): MoviesApi =
        retrofit.create(MoviesApi::class.java)
    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: MoviesRoomDatabase.Callback) =
        Room
            .databaseBuilder(application, MoviesRoomDatabase::class.java, "movies_database")
            .addCallback(callback)
            .build()
    @Provides
    fun provideMyDao(db: MoviesRoomDatabase) = db.movieDao()
}