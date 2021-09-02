package com.combyn.tvshows.ui.listmoviesfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.combyn.tvshows.repositories.movie.MovieRepository
import java.lang.Error

class ListMoviesViewModelFactory(private val moviesRepository: MovieRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == ListMoviesViewModel::class.java) {
            @Suppress("UNCHECKED_CAST")
            return ListMoviesViewModel(moviesRepository) as T
        }
        throw Error("ListMoviesViewModelFactory doesn't support ${modelClass.simpleName}")
    }

}