package com.frozenproject.moviecatalogue.ui.catalogue.series.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries
import com.frozenproject.moviecatalogue.data.db.series.SeriesDetail
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.repository.CatalogueRepository
import io.reactivex.disposables.CompositeDisposable

class DetailSeriesViewModel(
    private val repository: CatalogueRepository, seriesId: Int
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val seriesDetails: LiveData<SeriesDetail> by lazy {
        repository.getDetailSeries(compositeDisposable, seriesId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        repository.getNetworkStateDetailSeries()
    }

    fun addToFavorite(data: ResultSeries) {
        repository.addToFavoriteSeries(data)
    }

    fun deleteFromFavorite(data: ResultSeries) {
        repository.unFavoriteSeries(data)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}