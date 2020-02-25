package com.frozenproject.moviecatalogue.ui.catalogue.home.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.repository.CatalogueRepository

class MovieListViewModel(
    private val catalogueRepository: CatalogueRepository
) : ViewModel() {

    val movieEntries by lazy {
        catalogueRepository.getAllMovies()
    }

    val networkState: LiveData<NetworkState> by lazy {
        catalogueRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return movieEntries.value?.isEmpty() ?: true
    }

}