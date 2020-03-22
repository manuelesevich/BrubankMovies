package com.example.brubankmovies.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.brubankmovies.network.Movie

/**
 * Database for storing movies. With global method to get access to it
 */
//@Database(entities = [Movie::class], version = 1, exportSchema = false)
//abstract class MovieDatabase : RoomDatabase() {
//
//    abstract val movieDatabaseDao: MovieDatabaseDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: MovieDatabase? = null
//
//        fun getInstance(context: Context): MovieDatabase {
//            synchronized(this) {
//                var instance = INSTANCE
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                            context.applicationContext,
//                            MovieDatabase::class.java,
//                            "favourite_movie_database"
//                        )
//                        .fallbackToDestructiveMigration()
//                        .build()
//                    INSTANCE = instance
//                }
//                return instance
//            }
//        }
//    }
//}