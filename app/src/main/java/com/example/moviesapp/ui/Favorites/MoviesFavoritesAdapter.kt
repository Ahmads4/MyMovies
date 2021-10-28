package com.example.moviesapp.ui.Favorites

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
import com.example.moviesapp.ui.Movies.IMAGE_BASE_URL

class MoviesFavoritesAdapter constructor(private val listener: OnItemClickListener) :
    ListAdapter<MoviesFav, MoviesFavoritesAdapter.MoviesListViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        val binding = MovieLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return MoviesListViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        val currentItem = getItem(position)

        //
        holder.binding.favoritesCheckbox.isChecked = currentItem.isFavorite
        holder.binding.favoritesCheckbox.setOnCheckedChangeListener { _, isChecked ->
            currentItem.isFavorite

        }

        if(holder.binding.favoritesCheckbox.isChecked ) {
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
                    val movie = MoviesResults.Movies(item.title, item.id, item.release_date, item.overview, item.vote_average, item.poster_path, item.original_language, item.isFavorite)
                    listener.onItemClick(movie)
                }
            }

            //Implementing unfavorite feature
            binding.favoritesCheckbox.setOnClickListener{
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    listener.onDeleteClick(item)
                    showToast("${item.title} removed from favorites")

                }

            }


        }



        fun bind(fav: MoviesFav) {
            binding.apply {
                Glide.with(itemView)
                    .load(IMAGE_BASE_URL + fav.poster_path)
                    .centerCrop()
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
        fun onDeleteClick(fav: MoviesFav)

    }



    companion object DiffCallback : DiffUtil.ItemCallback<MoviesFav>() {
        override fun areItemsTheSame(
            oldItem: MoviesFav,
            newItem: MoviesFav
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MoviesFav,
            newItem: MoviesFav
        ): Boolean {
            return oldItem == newItem
        }
    }


}
