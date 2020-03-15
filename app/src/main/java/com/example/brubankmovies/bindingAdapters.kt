package com.example.brubankmovies

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.brubankmovies.mainScreen.MovieGridAdapter
import com.example.brubankmovies.network.Movie

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Movie>?){
    val adapter = recyclerView.adapter as MovieGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(){

}

@BindingAdapter("movieApiStatus")
fun bindStatus(){

}