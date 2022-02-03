package com.example.moviesapp.ui.Movies

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.data.MoviesResults
import com.example.moviesapp.data.local.MoviesFav
import com.example.moviesapp.databinding.MovieLayoutBinding
import com.example.moviesapp.ui.Favorites.DaoViewModel

val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

class MoviesListAdapter constructor(
    private val listener: OnItemClickListener,
    daoViewModel: DaoViewModel,
) :
    ListAdapter<MoviesResults.Movies, MoviesListAdapter.MoviesListViewHolder>(DiffCallback) {
    private lateinit var fav: MoviesFav
    private val idList: List<Int>? = daoViewModel.idList.value
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        val binding = MovieLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        val currentItem = getItem(position)
        //Set the checkbox value to the isFavorite boolean value based on movie id
        if (idList != null) {
            holder.binding.favoritesCheckbox.isChecked = idList.contains(currentItem.id)
        }

        holder.binding.favoritesCheckbox.setOnCheckedChangeListener { _, isChecked ->
            currentItem.isFavorite
        }

        if (holder.binding.favoritesCheckbox.isChecked) {
            currentItem.isFavorite = true
        }

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class MoviesListViewHolder(val binding: MovieLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //On click listener to navigate to the details screen
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    listener.onItemClick(item)
                }
            }
        }
        //Favorites feature
        init {
            binding.favoritesCheckbox.setOnClickListener {
                if (binding.favoritesCheckbox.isChecked) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        item.isFavorite = true
                        fav = MoviesFav(
                            item.title,
                            item.id,
                            item.release_date,
                            item.overview,
                            item.vote_average,
                            item.poster_path,
                            item.original_language,
                            item.isFavorite
                        )
                        listener.onFavoriteClick(fav)
                    }

                    showToast("${fav.title} is added to your favorites")
                } else {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val item = getItem(position)
                        item.isFavorite = false
                        fav = MoviesFav(
                            item.title,
                            item.id,
                            item.release_date,
                            item.overview,
                            item.vote_average,
                            item.poster_path,
                            item.original_language,
                            item.isFavorite
                        )
                        listener.onDeleteClick(fav)
                    }

                    showToast("${fav.title} is removed from your favorites")
                }
            }
        }

        fun bind(movie: MoviesResults.Movies) {
            binding.apply {
                Glide.with(itemView)
                    .load(IMAGE_BASE_URL + movie.poster_path)
                    .override(223, 382)
                    .error(R.drawable.ic_baseline_error_outline_24)
                    .into(movieImage)
            }
        }

        private fun showToast(string: String) {
            Toast.makeText(itemView.context, string, Toast.LENGTH_SHORT).show()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(movie: MoviesResults.Movies)
        fun onFavoriteClick(fav: MoviesFav)
        fun onDeleteClick(fav: MoviesFav)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MoviesResults.Movies>() {
        override fun areItemsTheSame(
            oldItem: MoviesResults.Movies,
            newItem: MoviesResults.Movies
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MoviesResults.Movies,
            newItem: MoviesResults.Movies
        ): Boolean {
            return oldItem == newItem
        }
    }
}


