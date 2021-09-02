package com.combyn.tvshows.ui.homefragment

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeViewModelFactory(private val fragmentManager: FragmentManager) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == HomeViewModel::class.java) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(fragmentManager) as T
        }
        throw Error("HomeViewModelFactory doesn't support ${modelClass.simpleName}")
    }

}