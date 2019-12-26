package com.frozenproject.moviecatalogue.ui.catalogue.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frozenproject.moviecatalogue.data.repository.CatalogueRepository

class MovieViewModelFactory(
    private val repository: CatalogueRepository
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>):T{
        return MovieListViewModel(
            repository
        ) as T
    }
}