package com.example.brubankmovies.network

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Class that will be used for storing the information from the API
 */
//@Entity(tableName = "favourite_movie_table")
@Parcelize
class Movie(
    //@PrimaryKey(autoGenerate = false)
    val id: Int,
    //@ColumnInfo(name = "title")
    val title: String,
    //@ColumnInfo(name = "overview")
    val overview: String,
    @Json(name = "backdrop_path") val backdropPath: String?,
    //@ColumnInfo(name = "backdrop_url")
    val backdropUrl:String = "https://image.tmdb.org/t/p/original$backdropPath",
    @Json(name = "poster_path") val posterPath: String?,
    //@ColumnInfo(name = "poster_url")
    val posterUrl: String = "https://image.tmdb.org/t/p/original$posterPath",
    //@ColumnInfo(name = "genre_ids")
    @Json(name = "genre_ids") val genreIds: List<Int>?,
    //@ColumnInfo(name = "release_date")
    @Json(name = "release_date") val releaseDate: String
    //@ColumnInfo(name = "is_favourite")

):Parcelable {
}


