package com.combyn.tvshows.ui.listmoviesfragment.adapter

import androidx.recyclerview.widget.DiffUtil
import com.combyn.tvshows.model.Movie

class MovieDiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }
}