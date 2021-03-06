package com.combyn.tvshows.ui.homefragment

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.combyn.tvshows.R
import com.combyn.tvshows.ui.addmoviefragment.AddMovieFragment
import com.combyn.tvshows.ui.listmoviesfragment.ListMoviesFragment

class HomeViewModel(private val fragmentManager: FragmentManager) : ViewModel() {
    /**
     * Navigates user to add new movie fragment
     */
    fun onAddNewMovie() {
        val addMoviesFragment = AddMovieFragment()
        fragmentManager.beginTransaction().add(R.id.fragment_container, addMoviesFragment)
            .addToBackStack(AddMovieFragment::class.simpleName).commit()
    }

    /**
     * Navigates user to show all movies fragment
     */
    fun onShowAllMovies() {
        val listMoviesFragment = ListMoviesFragment()
        fragmentManager.beginTransaction().add(R.id.fragment_container, listMoviesFragment)
            .addToBackStack(ListMoviesFragment::class.simpleName).commit()
    }
}