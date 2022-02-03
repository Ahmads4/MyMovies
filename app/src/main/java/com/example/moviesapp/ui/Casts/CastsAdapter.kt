package com.example.moviesapp.ui.Casts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.data.remote.Casts.Casts
import com.example.moviesapp.databinding.CastLayoutBinding
import com.example.moviesapp.ui.Movies.IMAGE_BASE_URL

class CastsAdapter constructor(private val listener: OnCastClickListener) :
    ListAdapter<Casts.Crew, CastsAdapter.CastsViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CastsAdapter.CastsViewHolder {
        val binding = CastLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastsViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class CastsViewHolder(val binding: CastLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    listener.onItemClick(item)
                }
            }
        }

        fun bind(cast: Casts.Crew) {
            binding.apply {
                Glide.with(itemView)
                    .load(IMAGE_BASE_URL + cast.profile_path)
                    .error(R.drawable.ic_baseline_error_outline_24)
                    .into(castImage)
                castCharacter.text = cast.character
                castName.text = cast.name
            }
        }
    }

    interface OnCastClickListener {
        fun onItemClick(cast: Casts.Crew)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Casts.Crew>() {
        override fun areItemsTheSame(
            oldItem: Casts.Crew,
            newItem: Casts.Crew
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Casts.Crew,
            newItem: Casts.Crew
        ): Boolean {
            return oldItem == newItem
        }
    }
}