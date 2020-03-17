package com.example.brubankmovies.mainScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.brubankmovies.databinding.GridViewItemBinding
import com.example.brubankmovies.network.Movie

class MovieGridAdapter(val onClickListener: OnClickListener) : ListAdapter<Movie, MovieGridAdapter.MovieVielHolder>(DiffCallback){
    class MovieVielHolder(private var binding: GridViewItemBinding):
    RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movie = movie
            binding.executePendingBindings()
        }
    }

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

    class OnClickListener(val clickListener: (movie:Movie) -> Unit) {
        fun onClick(movie: Movie) = clickListener(movie)
    }

}