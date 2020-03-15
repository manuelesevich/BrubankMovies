package com.example.brubankmovies.mainScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.brubankmovies.network.Movie
import com.example.brubankmovies.network.MovieApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.Exception


enum class MovieApiStatus { LOADING, ERROR, DONE }


class MainScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val _apiStatus = MutableLiveData<MovieApiStatus>()
    private val apiStatus: LiveData<MovieApiStatus> get() = _apiStatus

    private val _movies = MutableLiveData<List<Movie>>()
    private val movies: LiveData<List<Movie>> get() = _movies

    private val _trendingMovies = MutableLiveData<List<Movie>>()
    private val trendingMovies: LiveData<List<Movie>> get() = _trendingMovies

    private val _navigateToSelectedMovie = MutableLiveData<Movie>()
    private val navigateToSelectedMovie: LiveData<Movie> get() = _navigateToSelectedMovie

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        //TODO add initial movie call here (trending movies)
        getTrendingMovies()
    }
    //TODO call trending movies to display on recyclerview
    private fun getTrendingMovies(){
        coroutineScope.launch {
            var getTrendingMoviesDeferred = MovieApi.retrofitService.searchTrendingMovies()
            try {
                _apiStatus.value = MovieApiStatus.LOADING
                val listResult = getTrendingMoviesDeferred.await()
                _apiStatus.value = MovieApiStatus.DONE
                _trendingMovies.value = listResult
            } catch (e: Exception) {
                _apiStatus.value = MovieApiStatus.ERROR
                _trendingMovies.value = ArrayList()
            }
        }
    }
    //TODO see how to handle userInput from fragment
    private fun getSearchedMovies(userInput: String){
        coroutineScope.launch {
            var getMoviesDeferred = MovieApi.retrofitService.searchMovies(userInput)
            try {
                _apiStatus.value = MovieApiStatus.LOADING
                val listResult = getMoviesDeferred.await()
                _apiStatus.value = MovieApiStatus.DONE
                _movies.value = listResult
            } catch (e: Exception) {
                _apiStatus.value = MovieApiStatus.ERROR
                _movies.value = ArrayList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun displayMovieDetails(movie: Movie){
        _navigateToSelectedMovie.value = movie
    }

    fun displayMovieDetailsComplete(){
        _navigateToSelectedMovie.value = null
    }

}