package com.example.brubankmovies

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.example.brubankmovies.mainScreen.MovieApiStatus
import com.example.brubankmovies.mainScreen.MovieGridAdapter
import com.example.brubankmovies.network.Movie
import java.net.URL

/**
 * Loads the [Movie] list into the [RecyclerView].
 * Also sets a dividing line between [RecyclerView] items.
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Movie>?){
    val divider = DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL)
    divider.setDrawable(ContextCompat.getDrawable(recyclerView.context!!, R.drawable.divider_line)!!)
    recyclerView.addItemDecoration(divider)
    val adapter = recyclerView.adapter as MovieGridAdapter
    adapter.submitList(data)
    Log.i("Binding Adapters", data?.size.toString())
}

/**
 * Uses [Glide] to load an URL image into the ImageView
 */
@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imgUrl: String?){
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imgUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation))
                .error(R.drawable.ic_missing)
            .into(imageView)
    }
    Log.i("Binding Adapters", imgUrl.toString())
}

/**
 * Binds the [Movie.title]
 */
@BindingAdapter("movieTitle")
fun bindTitle(textView: TextView, movieTitle: String?){
    textView.text = movieTitle
}

/**
 * Binds the [Movie.releaseDate] only using the first 4 digits of the string.
 * This results in only the year being displayed.
 */
@BindingAdapter("releaseYear")
fun bindDate(textView: TextView, movieReleaseDate: String?){
    textView.text = movieReleaseDate?.take(4)
}

/**
 * Binds the [Movie.genreIds] which come withe the below codes. Using when it
 * chooses the correct genre.
 */
@BindingAdapter("movieGenre")
fun bindGenre(textView: TextView, movieGenre: List<Int>){
    val movieGenre2: String
    try {
        movieGenre2 = when(movieGenre[0]) {
            28 -> "Action"
            12 -> "Adventure"
            16 -> "Animation"
            35 -> "Comedy"
            80 -> "Crime"
            99 -> "Documentary"
            18 -> "Drama"
            10751 -> "Family"
            14 -> "Fantasy"
            36 -> "History"
            27 -> "Horror"
            10402 -> "Music"
            9648 -> "Mystery"
            10749 -> "Romance"
            878 -> "Science Fiction"
            10770 -> "TV Movie"
            53 -> "Thriller"
            10752 -> "War"
            37 -> "Western"
            else -> "Various"
        }
        textView.text = movieGenre2
    } catch (e: Exception) {
        Log.i("Binding Adapters", e.toString())
        textView.text = R.string.missing_genre.toString()
    }
}
/**
 * This binding adapter displays the [MovieApiStatus] of the network request in an image view.  When
 * the request is loading, it displays a loading_animation.  If the request has an error, it
 * displays a broken image to reflect the connection error.  When the request is finished, it
 * hides the image view.
 */
@BindingAdapter("movieApiStatus")
fun bindStatus(statusImageView: ImageView, status: MovieApiStatus?){
    when(status) {
        MovieApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MovieApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        MovieApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}