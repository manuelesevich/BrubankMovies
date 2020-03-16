package com.example.brubankmovies

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.brubankmovies.mainScreen.MovieApiStatus
import com.example.brubankmovies.mainScreen.MovieGridAdapter
import com.example.brubankmovies.network.Movie

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Movie>?){
    val adapter = recyclerView.adapter as MovieGridAdapter
    adapter.submitList(data)
    Log.i("Binding Adapters", data?.size.toString())
}

// TODO ver si anda bien la imagen de carga
@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imgUrl: String?){
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imgUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation))
                .error(R.drawable.ic_broken_image)
            .into(imageView)
    }
    Log.i("Binding Adapters", imgUrl.toString())
}

@BindingAdapter("movieTitle")
fun bindTitle(textView: TextView, movieTitle: String?){
    textView.text = movieTitle
}

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