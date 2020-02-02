package com.frozenproject.moviecatalogue.ui.catalogue.movie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.frozenproject.moviecatalogue.data.db.movie.MovieDetail
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.repository.CatalogueRepository
import io.reactivex.disposables.CompositeDisposable

class MovieDetailViewModel(
    private val catalogueRepository: CatalogueRepository,
    movieId: Int
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val movieDetails: LiveData<MovieDetail> by lazy {
        catalogueRepository.getDetailMovies(compositeDisposable, movieId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        catalogueRepository.getNetworkStateDetail()
    }

    fun addToFavorite(data: ResultMovie) {
        catalogueRepository.addToFavorite(data)
    }

    fun deleteFromFavorite(data: ResultMovie) {
        catalogueRepository.unFavoriteMovie(data)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}