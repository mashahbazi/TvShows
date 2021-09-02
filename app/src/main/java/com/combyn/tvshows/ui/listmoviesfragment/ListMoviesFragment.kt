package com.combyn.tvshows.ui.listmoviesfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.combyn.tvshows.R
import com.combyn.tvshows.databinding.FragmentListMoviesBinding
import com.combyn.tvshows.repositories.movie.MovieRepository
import com.combyn.tvshows.services.networking.ApolloClientFactory
import com.combyn.tvshows.type.MovieOrder
import com.combyn.tvshows.ui.listmoviesfragment.adapter.ListMovieAdapter
import kotlinx.coroutines.launch


class ListMoviesFragment : Fragment() {
    private val spinnerItems = listOf(
        MovieOrder.createdAt_ASC,
        MovieOrder.createdAt_DESC,
        MovieOrder.releaseDate_ASC,
        MovieOrder.releaseDate_DESC,
        MovieOrder.title_ASC,
        MovieOrder.title_DESC,
    )
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
        setUpSpinner()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllMovies()
    }

    private fun setUpSpinner() {
        val listItemName = spinnerItems.map {
            it.rawValue.replace("_", " ").replace("DESC", "High to Low")
                .replace("ASC", "Low to High")
        }
        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.spinner_item, listItemName)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerOrder.adapter = arrayAdapter
        binding.spinnerOrder.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                getAllMovies(spinnerItems[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
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
    private fun getAllMovies(movieOrder: MovieOrder = MovieOrder.createdAt_DESC) {
        viewModel.getAllMovies(movieOrder).observe(viewLifecycleOwner, {
            lifecycleScope.launch {
                listMovieAdapter.submitData(it)
            }
        })
    }
}