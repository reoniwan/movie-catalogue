package com.frozenproject.moviecatalogue.ui.catalogue.series.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries
import com.frozenproject.moviecatalogue.data.repository.CatalogueRepository

class SeriesFavoriteViewModel(repository: CatalogueRepository) : ViewModel() {

    private val dataSeries = repository.getFavoriteSeries()
    private val liveDataSeries = LivePagedListBuilder(dataSeries, 10).build()

    val favSeries: LiveData<PagedList<ResultSeries>> get() = liveDataSeries
}