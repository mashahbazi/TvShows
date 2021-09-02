package com.combyn.tvshows.ui.listmoviesfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.combyn.tvshows.databinding.FragmentListMoviesBinding
import com.combyn.tvshows.repositories.movie.MovieRepository
import com.combyn.tvshows.services.networking.ApolloClientFactory
import com.combyn.tvshows.ui.listmoviesfragment.adapter.ListMovieAdapter
import kotlinx.coroutines.launch


class ListMoviesFragment : Fragment() {
    private val listMovieAdapter = ListMovieAdapter { onViewAttached() }
    private lateinit var viewModel: ListMoviesViewModel
    private lateinit var binding: FragmentListMoviesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(
                this, ListMoviesViewModelFactory(MovieRepository(ApolloClientFactory.apolloClient))
            ).get(ListMoviesViewModel::class.java)
        binding = FragmentListMoviesBinding.inflate(inflater, container, false)
        binding.rvListMovies.layoutManager = LinearLayoutManager(context)
        binding.rvListMovies.adapter = listMovieAdapter
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllMovies()
    }

    /**
     * Hides progress bar when first item attached to recycler view window
     */
    private fun onViewAttached() {
        if (binding.progressbar.visibility == View.VISIBLE) {
            binding.progressbar.visibility = View.GONE
        }
    }

    /**
     * Fetch all movies from view model and submit then to recycler view adapter
     */
    private fun getAllMovies() {
        viewModel.getAllMovies().observe(viewLifecycleOwner, {
            lifecycleScope.launch {
                listMovieAdapter.submitData(it)
            }
        })
    }
}