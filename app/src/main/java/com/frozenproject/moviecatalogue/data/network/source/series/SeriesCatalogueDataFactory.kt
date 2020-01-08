package com.frozenproject.moviecatalogue.data.network.source.series

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries
import com.frozenproject.moviecatalogue.data.network.APICatalogueInterface
import io.reactivex.disposables.CompositeDisposable

class SeriesCatalogueDataFactory(
    private val apiService: APICatalogueInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, ResultSeries>() {
    val seriesLiveDataSource = MutableLiveData<SeriesCatalogueDataSource>()

    override fun create(): DataSource<Int, ResultSeries> {
        val seriesDataSource =
            SeriesCatalogueDataSource(
                apiService,
                compositeDisposable
            )

        seriesLiveDataSource.postValue(seriesDataSource)
        return seriesDataSource
    }

}
