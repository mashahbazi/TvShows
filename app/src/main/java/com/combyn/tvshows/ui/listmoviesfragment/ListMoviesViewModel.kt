package com.combyn.tvshows.ui.listmoviesfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.combyn.tvshows.model.Movie
import com.combyn.tvshows.repositories.movie.MovieRepository

class ListMoviesViewModel(private val moviesRepository: MovieRepository) : ViewModel() {
    /**
     * Fetch all movies from movie repository.
     *
     * It will fetch them using paging. This mean it will load them when user scroll down
     * not all in first request.
     */
    fun getAllMovies(): LiveData<PagingData<Movie>> = moviesRepository.getAllMovies()
}