package com.frozenproject.moviecatalogue.ui.catalogue.search.series

import androidx.lifecycle.ViewModel
import com.frozenproject.moviecatalogue.data.repository.CatalogueRepository
import io.reactivex.disposables.CompositeDisposable

class FindSeriesViewModel(
    repository: CatalogueRepository,
    seriesTitle: String
):ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    //Series
    val searchSeriesEntries by lazy {
        repository.searchCatalogueSeries(compositeDisposable, seriesTitle)
    }

    val searchSeriesNetworkState by lazy {
        repository.getNetworkStateSearchSeries()
    }

    fun listSeriesIsEmpty(): Boolean {
        return searchSeriesEntries.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}