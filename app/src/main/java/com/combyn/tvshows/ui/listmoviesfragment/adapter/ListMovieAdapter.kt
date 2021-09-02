package com.combyn.tvshows.ui.listmoviesfragment.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.ConfigurationCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.combyn.tvshows.databinding.MovieItemBinding
import com.combyn.tvshows.model.Movie

/**
 * @param onViewAttached : a call back for detect when recycler view items attached to window.
 */
class ListMovieAdapter(val onViewAttached: () -> Unit) :
    PagingDataAdapter<Movie, ListMovieAdapter.ViewHolder>(MovieDiffUtilCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        onViewAttached()
    }

    class ViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movie = movie
            binding.local =
                ConfigurationCompat.getLocales(binding.root.resources.configuration).get(0)

            binding.notifyChange()
        }
    }
}