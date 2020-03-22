package com.example.brubankmovies.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY = "208ca80d1e219453796a7f9792d16776"

private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
private val httpClient = OkHttpClient.Builder().addInterceptor(logging)


/**
 * Build the Moshi object that Retrofit will be using.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with the [moshi]
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(httpClient.build())
    .build()

/**
 * Public interface to expose methods using GET
 */
interface MovieApiService{
    /**
     * Returns a deferred list of [Movie]. Can be used with await() if in a coroutine scope.
     * Querys the API with the user input.
     */
    @GET("search/movie?api_key=$API_KEY&language=en-US")
    fun searchMovies(@Query("query") userInput: String) : Deferred<MovieContainer>

    /**
     * Searches the current trending movies of the Movie Database
     */
    @GET("trending/movie/week?api_key=$API_KEY")
    fun searchTrendingMovies(@Query("page") resultPage: Int) : Deferred<MovieContainer>
}

/**
 * Initializes the Retrofit service
 */
object MovieApi {
    val retrofitService: MovieApiService by lazy { retrofit.create(MovieApiService::class.java) }
}