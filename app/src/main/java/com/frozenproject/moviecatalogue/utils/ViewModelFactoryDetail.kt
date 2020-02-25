package com.frozenproject.moviecatalogue.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frozenproject.moviecatalogue.data.repository.CatalogueRepository
import com.frozenproject.moviecatalogue.ui.catalogue.detail.MovieDetailViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactoryDetail(
    private val repository: CatalogueRepository,
    private val id: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                //Movie
                isAssignableFrom(MovieDetailViewModel::class.java) ->
                    MovieDetailViewModel(
                        repository,
                        id
                    )
                else ->
                    throw  IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}