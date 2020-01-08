package com.frozenproject.moviecatalogue.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries
import com.frozenproject.moviecatalogue.data.db.series.SeriesDetail
import com.frozenproject.moviecatalogue.data.network.APICatalogueInterface
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.network.POST_PAGE
import com.frozenproject.moviecatalogue.data.network.source.series.SeriesCatalogueDataFactory
import com.frozenproject.moviecatalogue.data.network.source.series.SeriesCatalogueDataSource
import com.frozenproject.moviecatalogue.data.network.source.series.SeriesDetailsNetworkDataSc
import io.reactivex.disposables.CompositeDisposable

class SeriesCatalogueRepository(
    private val apiService: APICatalogueInterface
) {

    private lateinit var seriesPage: LiveData<PagedList<ResultSeries>>
    private lateinit var seriesDataSourceFactory: SeriesCatalogueDataFactory

    private lateinit var seriesDetails: SeriesDetailsNetworkDataSc

    fun fetchLiveSeriesPageList(compositeDisposable: CompositeDisposable): LiveData<PagedList<ResultSeries>> {
        seriesDataSourceFactory =
            SeriesCatalogueDataFactory(
                apiService,
                compositeDisposable
            )

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PAGE)
            .build()

        seriesPage = LivePagedListBuilder(seriesDataSourceFactory, config)
            .build()

        return seriesPage
    }

    fun fetchSeriesDetails(
        compositeDisposable: CompositeDisposable,
        seriesId: Int
    ): LiveData<SeriesDetail> {

        seriesDetails =
            SeriesDetailsNetworkDataSc(
                apiService,
                compositeDisposable
            )
        seriesDetails.fetchSeriesDetails(seriesId)

        return seriesDetails.downloadedSeriesResponse
    }

    fun getNetworkStateSeries(): LiveData<NetworkState> {
        return Transformations.switchMap<SeriesCatalogueDataSource, NetworkState>(
            seriesDataSourceFactory.seriesLiveDataSource,
            SeriesCatalogueDataSource::networkState
        )
    }

    fun getSeriesDetailsNetworkState(): LiveData<NetworkState> {
        return seriesDetails.networkState
    }

}