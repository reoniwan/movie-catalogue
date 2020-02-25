package com.frozenproject.moviecatalogue.ui.catalogue.detail

import androidx.lifecycle.ViewModel
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.db.series.ResultSeries
import com.frozenproject.moviecatalogue.data.repository.CatalogueRepository
import com.frozenproject.moviecatalogue.utils.lazyDeferred

class MovieDetailViewModel(
    private val catalogueRepository: CatalogueRepository,
    private val id: Int
) : ViewModel() {

    val movieDetails by lazyDeferred {
        catalogueRepository.getMovieDetails(id)
    }

    suspend fun addMovieFavorite(movie: ResultMovie) {
        catalogueRepository.updateMovie(movie)
    }

    //series
    val seriesDetails by lazyDeferred {
        catalogueRepository.getSeriesDetails(id)
    }

    suspend fun addSeriesFavorite(series: ResultSeries) {
        catalogueRepository.updateSeries(series)
    }
}