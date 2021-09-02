package com.combyn.tvshows.ui.addmoviefragment

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.*
import com.combyn.tvshows.R
import com.combyn.tvshows.model.Movie
import com.combyn.tvshows.repositories.movie.MovieRepository
import com.combyn.tvshows.utils.DateFormatter
import kotlinx.coroutines.launch
import java.util.*

class AddMovieViewModel(
    private val movieRepository: MovieRepository,
    private val supportFragmentManager: FragmentManager,
    private val locale: Locale
) : ViewModel() {
    val tvShowTitle = MutableLiveData<String>()
    val tvShowTitleError = MutableLiveData<String?>()
    val seasons = MutableLiveData<String>()
    val releaseDate = MutableLiveData<Date>()
    val isLoadingVisibility = MutableLiveData(false)

    /**
     * Maps {@param AddMovieViewModel.releaseDate} to a human read able string
     */
    fun getHumanReadableDate(): LiveData<String> {
        return releaseDate.map { DateFormatter.formatDate(it, locale) }
    }

    /**
     * Save movie data in movie repository and pop from current screen.
     * 
     * It will show error message when require fields are empty.
     */
    fun saveTVShow() {
        val title = tvShowTitle.value
        if (title != null) {
            isLoadingVisibility.value = true
            tvShowTitleError.value = null
            val seasonNumbers = seasons.value?.toDoubleOrNull()
            viewModelScope.launch {
                createMovie(title, seasonNumbers, releaseDate.value)
                popBack()
            }
        } else {
            tvShowTitleError.value = "This field Can't be empty"
        }
    }

    /**
     * Create a movie in movie repository using input data
     */
    private suspend fun createMovie(title: String, season: Double?, releaseDate: Date?) {
        val movie = Movie(title, season, releaseDate)
        movieRepository.createMovie(movie)
    }

    /**
     * Pop of current fragment and show below fragment in stack.
     */
    private fun popBack() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment != null && fragment is AddMovieFragment) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
            supportFragmentManager.popBackStackImmediate()
        }
    }
}