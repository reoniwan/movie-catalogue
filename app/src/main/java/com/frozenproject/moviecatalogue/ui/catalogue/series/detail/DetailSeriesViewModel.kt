package com.frozenproject.moviecatalogue.ui.catalogue.series.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.frozenproject.moviecatalogue.data.db.series.SeriesDetail
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.repository.SeriesCatalogueRepository
import io.reactivex.disposables.CompositeDisposable

class DetailSeriesViewModel(
    private val seriesRepository: SeriesCatalogueRepository, seriesId: Int
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val seriesDetails: LiveData<SeriesDetail> by lazy {
        seriesRepository.fetchSeriesDetails(compositeDisposable, seriesId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        seriesRepository.getSeriesDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}