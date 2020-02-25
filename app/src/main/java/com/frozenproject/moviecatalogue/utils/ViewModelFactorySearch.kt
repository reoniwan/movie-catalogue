package com.frozenproject.moviecatalogue.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frozenproject.moviecatalogue.data.repository.CatalogueRepository
import com.frozenproject.moviecatalogue.ui.catalogue.search.movie.FindMovieViewModel
import com.frozenproject.moviecatalogue.ui.catalogue.search.series.FindSeriesViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactorySearch(
    private val repository: CatalogueRepository,
    private val query: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                //Movie
                isAssignableFrom(FindMovieViewModel::class.java) ->
                    FindMovieViewModel(repository, query)
                //Series
                isAssignableFrom(FindSeriesViewModel::class.java) ->
                    FindSeriesViewModel(repository, query)
                else ->
                    throw  IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}