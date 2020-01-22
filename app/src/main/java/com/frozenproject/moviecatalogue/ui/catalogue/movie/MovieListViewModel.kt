package com.frozenproject.moviecatalogue.ui.catalogue.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.repository.favorite.MovieCatalogueRepository
import io.reactivex.disposables.CompositeDisposable

class MovieListViewModel(
    private val movieRepository: MovieCatalogueRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieEntries by lazy {
        movieRepository.getAllMovies(compositeDisposable)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return movieEntries.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}