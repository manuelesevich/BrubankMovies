package com.example.brubankmovies.detailScreen

import android.app.Application
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.brubankmovies.network.Movie
import kotlinx.coroutines.*
import java.io.InputStream
import java.lang.Exception
import java.net.URL

/**
 * ViewModel for Detail Screen Fragment.
 */
class DetailScreenViewModel(movie: Movie, app: Application) : AndroidViewModel(app) {
    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie> get() = _selectedMovie
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        _selectedMovie.value = movie
    }

//    fun loadBackdropImage(layout: ConstraintLayout) {
//        var drawable: Drawable
//        coroutineScope.launch {
//            try {
//                //val inputStream = java.net.URL(_selectedMovie.value?.backdropUrl).content as InputStream
//                //Drawable.createFromStream(inputStream, "backdrop poster")
//                val url = URL(_selectedMovie.value?.backdropUrl)
//                val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
//                drawable = BitmapDrawable(bitmap)
//                layout.background = drawable
//            } catch (e: Exception) {
//                Log.i("DetailScrenViewModel", e.toString())
//            }
//        }
//    }
}