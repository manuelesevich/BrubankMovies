package com.example.brubankmovies.mainScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.brubankmovies.databinding.GridViewItemBinding
import com.example.brubankmovies.network.Movie

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class MovieGridAdapter(val onClickListener: OnClickListener) : ListAdapter<Movie, MovieGridAdapter.MovieVielHolder>(DiffCallback){

    class MovieVielHolder(private var binding: GridViewItemBinding):
    RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movie = movie
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Movie]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVielHolder {
        return MovieVielHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieVielHolder, position: Int) {
        val clickedMovie =getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(clickedMovie)
        }
        holder.bind(clickedMovie)
    }

    /**
     * Listener that handles clicks on the RecyclerView items.
     * Passes the [Movie] of the current item to the onClick function.
     */
    class OnClickListener(val clickListener: (movie:Movie) -> Unit) {
        fun onClick(movie: Movie) = clickListener(movie)
    }

}