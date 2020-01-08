package com.frozenproject.moviecatalogue.ui.catalogue.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.repository.SeriesCatalogueRepository
import io.reactivex.disposables.CompositeDisposable

class SeriesListViewModel(
    private val seriesRepository: SeriesCatalogueRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val seriesEntries by lazy {
        seriesRepository.fetchLiveSeriesPageList(compositeDisposable)
    }

    val networkState: LiveData<NetworkState> by lazy {
        seriesRepository.getNetworkStateSeries()
    }

    fun listIsEmpty(): Boolean {
        return seriesEntries.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}