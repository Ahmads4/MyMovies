package com.example.moviesapp

import android.app.Application
import com.example.moviesapp.data.local.MoviesRoomDatabase
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MoviesApplication: Application() {
    val database : MoviesRoomDatabase by lazy { MoviesRoomDatabase.getDatabase(this)}

}