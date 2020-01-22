package com.frozenproject.moviecatalogue.ui.catalogue.movie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.frozenproject.moviecatalogue.data.db.movie.MovieDetail
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.repository.favorite.MovieCatalogueRepository
import io.reactivex.disposables.CompositeDisposable

class MovieDetailViewModel(
    private val movieRepository: MovieCatalogueRepository,
    movieId: Int
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val movieDetails: LiveData<MovieDetail> by lazy {
        movieRepository.getDetailMovies(compositeDisposable, movieId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getNetworkStateDetail()
    }

    fun addToFavorite(data: ResultMovie){
        movieRepository.addToFavorite(data)
    }

    fun deleteFromFavorite(data: ResultMovie){
        movieRepository.unFavoriteMovie(data)
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}