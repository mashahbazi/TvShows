package com.combyn.tvshows.ui.addmoviefragment

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.combyn.tvshows.repositories.movie.MovieRepository
import java.util.*

class AddMovieViewModelFactory(
    private val movieRepository: MovieRepository,
    private val supportFragmentManager: FragmentManager,
    private val locale: Locale,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == AddMovieViewModel::class.java) {
            val viewModel = AddMovieViewModel(movieRepository, supportFragmentManager, locale)
            @Suppress("UNCHECKED_CAST")
            return modelClass.cast(viewModel) as T
        }
        throw Error("AddMovieViewModelFactory doesn't support ${modelClass.simpleName}")
    }
}