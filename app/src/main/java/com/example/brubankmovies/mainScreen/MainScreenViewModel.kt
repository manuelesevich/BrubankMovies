package com.example.brubankmovies.mainScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.brubankmovies.network.Movie
import com.example.brubankmovies.network.MovieApi
import kotlinx.coroutines.*
import kotlin.Exception


enum class MovieApiStatus { LOADING, ERROR, DONE }


/**
 * ViewModel for Main Screen Fragment
 */
class MainScreenViewModel(/*val database: MovieDatabaseDao*/) : ViewModel() {

    //val favouriteMovies = database.getAllMovies()

    /**
     * Used to request more movies
     */
    private var trendingPageCounter = 2

    // The external immutable LiveData for the request status
    private val _apiStatus = MutableLiveData<MovieApiStatus>()
    val apiStatus: LiveData<MovieApiStatus> get() = _apiStatus

    /**
     * Used to update the list of movies. Internal use
     */
    private val _trendingMovies = MutableLiveData<List<Movie>>()

    /**
     * For external use.
     */
    val trendingMovies: LiveData<List<Movie>> get() = _trendingMovies

    /**
     * Used internally to handle navigation to the selected movie.
     */
    private val _navigateToSelectedMovie = MutableLiveData<Movie>()

    /**
     * The external immutable LiveData for the navigation property
     */
    val navigateToSelectedMovie: LiveData<Movie> get() = _navigateToSelectedMovie

    /**
     * Coroutine scope using a job to be able to cancel when needed
     */
    private var viewModelJob = Job()

    /**
     * the Coroutine runs using the Main (UI) dispatcher
     */
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var _showSnackbarEvent = MutableLiveData<Boolean>()
    val showSnackbarEvent: LiveData<Boolean> get() = _showSnackbarEvent

    /**
     * Used to navigate to favourite screen
     */
    private var _navigateToFavouritesScreen = MutableLiveData<Boolean>()
    val navigateToFavouritesScreen: LiveData<Boolean> get() = _navigateToFavouritesScreen

    /**
     * call to stop showing toast in case of rotation of phone
     */
    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }

    // Call getTrendingMovies to get the trending movies once the app opens
    init {
        getTrendingMovies()
        //getSearchedMovies("max")
    }

    /**
     * Gets movies from the Movie API using retrofi and updates the movie list.
     * Retrofit service returns a coroutine deferred which we wait to get the
     * result of the transaction.
     */
    private fun getTrendingMovies(){
        coroutineScope.launch {
            var getTrendingMoviesDeferred = MovieApi.retrofitService.searchTrendingMovies(1)
            try {
                _apiStatus.value = MovieApiStatus.LOADING
                val listResult = getTrendingMoviesDeferred.await()
                _apiStatus.value = MovieApiStatus.DONE
                _trendingMovies.value = listResult.results
                Log.i("MainScreenViewModel","list size is ${listResult.results.size}")
                Log.i("MainScreenViewModel",listResult.results[0].posterUrl)
            } catch (e: Exception) {
                _apiStatus.value = MovieApiStatus.ERROR
                Log.i("MainScreenViewModel", "$e")
                _trendingMovies.value = ArrayList()
            }
        }
    }

    /**
     * Same as [getTrendingMovies] but used for pagination purposes.
     * Copies the list of movies, adds the new movies and updates the list with all the results.
     */
    fun loadMoreTrendingMovies() {
        coroutineScope.launch {
            var getTrendingMoviesDeferred = MovieApi.retrofitService.searchTrendingMovies(trendingPageCounter)
            try {
                _apiStatus.value = MovieApiStatus.LOADING
                val listResult = getTrendingMoviesDeferred.await()
                val tempList = ArrayList<Movie>()
                tempList.addAll(_trendingMovies.value!!)
                tempList.addAll(listResult.results)
                _trendingMovies.value = tempList
                trendingPageCounter++
                _apiStatus.value = MovieApiStatus.DONE
                Log.i("MainScreenViewModel","list size is ${listResult.results.size}")
                Log.i("MainScreenViewModel",listResult.results[0].posterUrl)
            } catch (e: Exception) {
                _apiStatus.value = MovieApiStatus.ERROR
                Log.i("MainScreenViewModel", "$e")
                //_trendingMovies.value = ArrayList()
            }
        }    }

    /**
     * same as [getTrendingMovies] but uses user input to Query the API.
     */
    private fun getSearchedMovies(userInput: String){
        coroutineScope.launch {
            var getTrendingMoviesDeferred = MovieApi.retrofitService.searchMovies(userInput)
            try {
                _apiStatus.value = MovieApiStatus.LOADING
                _trendingMovies.value = ArrayList()
                val listResult = getTrendingMoviesDeferred.await()
                val tempList = ArrayList<Movie>()
                tempList.addAll(_trendingMovies.value!!)
                tempList.addAll(listResult.results)
                _trendingMovies.value = tempList
                _apiStatus.value = MovieApiStatus.DONE
                Log.i("MainScreenViewModel","list size is ${listResult.results.size}")
                Log.i("MainScreenViewModel",listResult.results[0].posterUrl)
            } catch (e: Exception) {
                _apiStatus.value = MovieApiStatus.ERROR
                Log.i("MainScreenViewModel", "$e")
                _trendingMovies.value = ArrayList()
            }
        }
    }

    /**
     * Cancels the coroutine once the ViewModel is finished
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * When a movie is clicked, sets [_navigateToSelectedMovie] to that movie.
     */
    fun displayMovieDetails(movie: Movie){
        _navigateToSelectedMovie.value = movie
    }

    /**
     * Clears navigation request in case user rotates phone
     */
    fun displayMovieDetailsComplete(){
        _navigateToSelectedMovie.value = null
    }

    /**
     * Calls [getSearchedMovies] with the string input by the user.
     */
    fun searchMovies(userInput: String){
        getSearchedMovies(userInput)
    }

    /**
     * insert movie into database
     */
    private suspend fun insert(movie: Movie) {
        withContext(Dispatchers.IO) {
            //database.insert(movie)
        }
    }

}