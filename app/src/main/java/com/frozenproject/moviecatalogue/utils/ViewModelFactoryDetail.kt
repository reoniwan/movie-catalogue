package com.frozenproject.moviecatalogue.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frozenproject.moviecatalogue.data.repository.CatalogueRepository
import com.frozenproject.moviecatalogue.ui.catalogue.movie.detail.MovieDetailViewModel
import com.frozenproject.moviecatalogue.ui.catalogue.search.movie.FindMovieViewModel
import com.frozenproject.moviecatalogue.ui.catalogue.search.series.FindSeriesViewModel
import com.frozenproject.moviecatalogue.ui.catalogue.series.detail.DetailSeriesViewModel
import java.lang.IllegalArgumentException

class ViewModelFactoryDetail(
    private val repository: CatalogueRepository,
    private val id: Int
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                //Movie
                isAssignableFrom(MovieDetailViewModel::class.java) ->
                    MovieDetailViewModel(repository, id)
                //Series
                isAssignableFrom(DetailSeriesViewModel::class.java) ->
                    DetailSeriesViewModel(repository, id)
                else ->
                    throw  IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}