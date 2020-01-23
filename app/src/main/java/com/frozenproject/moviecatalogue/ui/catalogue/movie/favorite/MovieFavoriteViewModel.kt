package com.frozenproject.moviecatalogue.ui.catalogue.movie.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.repository.favorite.MovieCatalogueRepository

class MovieFavoriteViewModel(repository: MovieCatalogueRepository): ViewModel() {

    val dataMovies = repository.getFavoriteMovies()
}