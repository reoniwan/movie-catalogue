package com.frozenproject.moviecatalogue.ui.catalogue.movie.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.repository.CatalogueRepository

class MovieFavoriteViewModel(repository: CatalogueRepository) : ViewModel() {

    private val dataMovies = repository.getFavoriteMovies()
    private val liveDataMovies = LivePagedListBuilder(dataMovies, 10).build()

    val favMovies: LiveData<PagedList<ResultMovie>> get() = liveDataMovies
}