package com.frozenproject.moviecatalogue.data.network.source.series.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries
import com.frozenproject.moviecatalogue.data.network.APICatalogueInterface
import io.reactivex.disposables.CompositeDisposable

class FindSeriesDataFactory(
    private val compositeDisposable: CompositeDisposable,
    private val apiService: APICatalogueInterface,
    private val query: String
):DataSource.Factory<Int, ResultSeries>() {

    val searchDataSource = MutableLiveData<FindSeriesDataSource>()

    override fun create(): DataSource<Int, ResultSeries> {
        val sourceData =
            FindSeriesDataSource(
                compositeDisposable,
                apiService,
                query
            )

        searchDataSource.postValue(sourceData)

        return sourceData
    }
}