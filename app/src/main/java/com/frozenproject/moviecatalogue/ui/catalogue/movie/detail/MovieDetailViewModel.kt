package com.frozenproject.moviecatalogue.ui.catalogue.movie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frozenproject.moviecatalogue.data.db.movie.MovieDetail
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.repository.favorite.MovieCatalogueRepository
import io.reactivex.disposables.CompositeDisposable

class MovieDetailViewModel(
    private val movieRepository: MovieCatalogueRepository,
    movieId: Int
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _detail = MutableLiveData<MovieDetail>()

    val detail: LiveData<MovieDetail> get() = _detail

    fun setData(data: MovieDetail){
        _detail.postValue(data)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getNetworkStateDetail()
    }

    fun addToFavorite(data: MovieDetail){
        movieRepository.addToFavorite(data)
    }

    fun deleteFromFavorite(data: MovieDetail){
        movieRepository.unFavoriteMovie(data)
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}