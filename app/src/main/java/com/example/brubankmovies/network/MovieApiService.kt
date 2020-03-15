package com.example.brubankmovies.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org/3/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface MovieApiService{
    //TODO check the length of GET and if QUERY is correct
    @GET("search/movie?api_key=208ca80d1e219453796a7f9792d16776&language=en-US")
    fun searchMovies(@Query("query") userInput: String) : Deferred<List<Movie>>

    @GET("trending/movie/week?api_key=208ca80d1e219453796a7f9792d16776")
    fun searchTrendingMovies() : Deferred<List<Movie>>
}

object MovieApi {
    val retrofitService: MovieApiService by lazy { retrofit.create(MovieApiService::class.java) }
}