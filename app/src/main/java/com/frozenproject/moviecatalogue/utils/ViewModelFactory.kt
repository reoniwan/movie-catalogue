package com.frozenproject.moviecatalogue.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frozenproject.moviecatalogue.data.network.CATALOGUE_ID
import com.frozenproject.moviecatalogue.data.repository.favorite.MovieCatalogueRepository
import com.frozenproject.moviecatalogue.ui.catalogue.movie.MovieListViewModel
import com.frozenproject.moviecatalogue.ui.catalogue.movie.detail.MovieDetailActivity
import com.frozenproject.moviecatalogue.ui.catalogue.movie.detail.MovieDetailViewModel
import com.frozenproject.moviecatalogue.ui.catalogue.movie.favorite.MovieFavoriteViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val repository: MovieCatalogueRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass){
            when{
                isAssignableFrom(MovieListViewModel::class.java) ->
                    MovieListViewModel(repository)
                isAssignableFrom(MovieDetailViewModel::class.java) ->
                    MovieDetailViewModel(repository, MovieDetailActivity.movieId)
                isAssignableFrom(MovieFavoriteViewModel::class.java) ->
                    MovieFavoriteViewModel(repository)
                else ->
                    throw  IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}