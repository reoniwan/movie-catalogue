package com.frozenproject.moviecatalogue.ui.catalogue.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.repository.favorite.MovieCatalogueRepository
import io.reactivex.disposables.CompositeDisposable

class MovieListViewModel(
    movieRepository: MovieCatalogueRepository
) : ViewModel() {

    val movieList = movieRepository.getAllMovies()
}