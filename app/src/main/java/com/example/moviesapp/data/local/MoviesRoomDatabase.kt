package com.example.moviesapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import javax.inject.Inject
import javax.inject.Provider


@Database(entities = [MoviesFav::class], version = 1, exportSchema = false)
abstract class MoviesRoomDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    class Callback @Inject constructor(
        private val database: Provider<MoviesRoomDatabase>,

        ) : RoomDatabase.Callback() {
        //Oncreate is only called once, when the database is created
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

        }

    }

    companion object {

        //Volatile variable will never be cached
        //Makes sure INSTANCE is always up-to-date and same for all execution threads
        //Changes made by one thread to INSTANCE are visible to all other threads immediately
        @Volatile

        //INSTANCE will keep a reference to the database. This helps in maintaining one instance of the database opened since it is an expensive resource to create and maintain
        private var INSTANCE: MoviesRoomDatabase? = null

        fun getDatabase(context: Context): MoviesRoomDatabase {

            //Wrapping code to get database inside synchronized block means that only one thread of execution can enter this block of code, making sure database is initialized only once
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoviesRoomDatabase::class.java,
                    "movies_favorites"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }

        }


    }


}