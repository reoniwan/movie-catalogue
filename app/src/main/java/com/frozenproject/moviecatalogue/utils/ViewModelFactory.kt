package com.frozenproject.moviecatalogue.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frozenproject.moviecatalogue.data.repository.CatalogueRepository
import com.frozenproject.moviecatalogue.ui.catalogue.movie.MovieListViewModel
import com.frozenproject.moviecatalogue.ui.catalogue.movie.detail.MovieDetailActivity
import com.frozenproject.moviecatalogue.ui.catalogue.movie.detail.MovieDetailViewModel
import com.frozenproject.moviecatalogue.ui.catalogue.movie.favorite.MovieFavoriteViewModel
import com.frozenproject.moviecatalogue.ui.catalogue.search.FindCatalogueActivity
import com.frozenproject.moviecatalogue.ui.catalogue.search.FindCatalogueFragment
import com.frozenproject.moviecatalogue.ui.catalogue.search.FindMovieViewModel
import com.frozenproject.moviecatalogue.ui.catalogue.search.series.FindSeriesFragment
import com.frozenproject.moviecatalogue.ui.catalogue.search.series.FindSeriesViewModel
import com.frozenproject.moviecatalogue.ui.catalogue.series.SeriesListViewModel
import com.frozenproject.moviecatalogue.ui.catalogue.series.detail.DetailSeriesActivity
import com.frozenproject.moviecatalogue.ui.catalogue.series.detail.DetailSeriesViewModel
import com.frozenproject.moviecatalogue.ui.catalogue.series.favorite.SeriesFavoriteViewModel
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
                    MovieListViewModel(repository)
                isAssignableFrom(MovieDetailViewModel::class.java) ->
                    MovieDetailViewModel(repository, MovieDetailActivity.movieId)
                isAssignableFrom(MovieFavoriteViewModel::class.java) ->
                    MovieFavoriteViewModel(repository)
                isAssignableFrom(FindMovieViewModel::class.java) ->
                    FindMovieViewModel(repository, FindCatalogueFragment.movieTitle)
                //Series
                isAssignableFrom(SeriesListViewModel::class.java) ->
                    SeriesListViewModel(repository)
                isAssignableFrom(DetailSeriesViewModel::class.java) ->
                    DetailSeriesViewModel(repository, DetailSeriesActivity.seriesId)
                isAssignableFrom(SeriesFavoriteViewModel::class.java) ->
                    SeriesFavoriteViewModel(repository)
                isAssignableFrom(FindSeriesViewModel::class.java) ->
                    FindSeriesViewModel(repository, FindSeriesFragment.seriesTitle)
                else ->
                    throw  IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}