package com.frozenproject.moviecatalogue.ui.catalogue.search.movie

import androidx.lifecycle.ViewModel
import com.frozenproject.moviecatalogue.data.repository.CatalogueRepository
import io.reactivex.disposables.CompositeDisposable

class FindMovieViewModel(
    private val repository: CatalogueRepository,
    private val movieTitle: String
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    //Movie
    val searchMovieEntries by lazy {
        repository.searchCatalogueMovie(compositeDisposable, movieTitle)
    }

    val searchMovieNetworkState by lazy {
        repository.getNetworkStateSearchMovie()
    }

    fun listIsEmpty(): Boolean {
        return searchMovieEntries.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}