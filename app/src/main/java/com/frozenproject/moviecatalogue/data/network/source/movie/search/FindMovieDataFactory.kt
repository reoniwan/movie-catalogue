package com.frozenproject.moviecatalogue.data.network.source.movie.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.network.APICatalogueInterface
import io.reactivex.disposables.CompositeDisposable

class FindMovieDataFactory(
    private val compositeDisposable: CompositeDisposable,
    private val apiService: APICatalogueInterface,
    private val query: String
) : DataSource.Factory<Int, ResultMovie>() {

    val searchDataSource = MutableLiveData<FindMovieDataSource>()

    override fun create(): DataSource<Int, ResultMovie> {

        val sourceData = FindMovieDataSource(
            compositeDisposable,
            apiService,
            query
        )

        searchDataSource.postValue(sourceData)

        return sourceData

    }
}