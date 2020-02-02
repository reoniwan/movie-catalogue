package com.frozenproject.moviecatalogue.ui.catalogue.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.frozenproject.moviecatalogue.data.network.NetworkState
import com.frozenproject.moviecatalogue.data.repository.CatalogueRepository
import io.reactivex.disposables.CompositeDisposable

class SeriesListViewModel(
    private val repository: CatalogueRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val seriesEntries by lazy {
        repository.getAllSeries(compositeDisposable)
    }

    val networkState: LiveData<NetworkState> by lazy {
        repository.getNetworkStateSeries()
    }

    fun listIsEmpty(): Boolean {
        return seriesEntries.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}