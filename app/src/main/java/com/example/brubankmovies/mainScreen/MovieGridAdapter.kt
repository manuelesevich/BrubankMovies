package com.example.brubankmovies.mainScreen

import androidx.recyclerview.widget.ListAdapter
import com.example.brubankmovies.network.Movie

class MovieGridAdapter() : ListAdapter<Movie, MovieGridAdapter.MovieVielHolder>(DiffCallback){
    class MovieVielHolder(private var binding) {

    }

}