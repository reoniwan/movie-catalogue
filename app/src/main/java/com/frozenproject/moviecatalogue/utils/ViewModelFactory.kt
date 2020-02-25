package com.frozenproject.moviecatalogue.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frozenproject.moviecatalogue.data.repository.CatalogueRepository
import com.frozenproject.moviecatalogue.ui.catalogue.home.movie.MovieListViewModel
import com.frozenproject.moviecatalogue.ui.catalogue.home.series.SeriesListViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val repository: CatalogueRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                //Movie
                isAssignableFrom(MovieListViewModel::class.java) ->
                    MovieListViewModel(
                        repository
                    )
                //Series
                isAssignableFrom(SeriesListViewModel::class.java) ->
                    SeriesListViewModel(
                        repository
                    )
                else ->
                    throw  IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}