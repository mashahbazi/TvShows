package com.combyn.tvshows.ui.addmoviefragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.combyn.tvshows.databinding.DatePickerDialogBinding
import com.combyn.tvshows.databinding.FragmentAddMovieBinding
import com.combyn.tvshows.repositories.movie.MovieRepository
import com.combyn.tvshows.services.networking.ApolloClientFactory
import java.util.*


class AddMovieFragment : Fragment() {
    lateinit var viewModel: AddMovieViewModel
    lateinit var binding: FragmentAddMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val locale = ConfigurationCompat.getLocales(resources.configuration).get(0)
        viewModel = ViewModelProvider(
            this,
            AddMovieViewModelFactory(
                MovieRepository(ApolloClientFactory.apolloClient),
                requireActivity().supportFragmentManager,
                locale
            )
        ).get(AddMovieViewModel::class.java)
        binding = FragmentAddMovieBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.tvReleaseDate.setOnClickListener { showDatePicker() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.tvShowTitleError.observe(viewLifecycleOwner,
            { binding.etTvShowTitle.error = it })
    }

    private fun showDatePicker() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding = DatePickerDialogBinding.inflate(LayoutInflater.from(requireContext()))
        val calendar = Calendar.getInstance()
        binding.datePicker.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ) { _, year, monthOfYear, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(year, monthOfYear, dayOfMonth)
            viewModel.releaseDate.postValue(selectedCalendar.time)
        }
        binding.btnOk.setOnClickListener { dialog.dismiss() }
        dialog.setContentView(binding.root)
        dialog.show()
    }
}