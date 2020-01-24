package com.frozenproject.moviecatalogue.data.network.source.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.network.APICatalogueInterface
import io.reactivex.disposables.CompositeDisposable

class MovieCatalogueDataFactory(
    private val apiService: APICatalogueInterface
) : DataSource.Factory<Int, ResultMovie>() {
    private val movieLiveDataSource = MutableLiveData<MovieCatalogueDataSource>()

    override fun create(): DataSource<Int, ResultMovie> {
        val movieDataSource = MovieCatalogueDataSource(apiService)

        movieLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}