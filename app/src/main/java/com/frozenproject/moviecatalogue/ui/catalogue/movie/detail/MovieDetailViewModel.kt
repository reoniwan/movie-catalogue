package com.frozenproject.moviecatalogue.ui.catalogue.movie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.repository.favorite.MovieCatalogueRepository
import io.reactivex.disposables.CompositeDisposable

class MovieDetailViewModel(
    private val movieRepository: MovieCatalogueRepository
) : ViewModel() {

    private val _detail = MutableLiveData<ResultMovie>()

    val detail: LiveData<ResultMovie> get() = _detail

    fun setData(data: ResultMovie){
        _detail.postValue(data)
    }

    fun addToFavorite(data: ResultMovie){
        movieRepository.addToFavorite(data)
    }

    fun deleteFromFavorite(data: ResultMovie){
        movieRepository.unFavoriteMovie(data)
    }
}