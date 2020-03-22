package com.example.brubankmovies.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.brubankmovies.network.Movie

/**
 * Defines methods for using the Movie class with Room.
 */
//@Dao
//interface MovieDatabaseDao {
//    @Insert
//    fun insert(movie: Movie)
//
//    @Update
//    fun update(movie: Movie)
//
//    @Query("SELECT * FROM favourite_movie_table WHERE id = :key")
//    fun get(key: Int): Movie?
//
//    @Query("DELETE FROM favourite_movie_table")
//    fun clear()
//
//    @Query("SELECT * FROM favourite_movie_table ORDER BY id DESC")
//    fun getAllMovies(): LiveData<List<Movie>>
//
//    @Query("SELECT * FROM favourite_movie_table ORDER BY id DESC LIMIT 1")
//    fun getLatestAddedMovie(): Movie?
//
//    @Query("SELECT * FROM favourite_movie_table WHERE id = :key")
//    fun getMovieById(key: Int): LiveData<List<Movie>>
//}