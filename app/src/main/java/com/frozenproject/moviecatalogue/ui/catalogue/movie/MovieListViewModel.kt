package com.frozenproject.moviecatalogue.ui.catalogue.movie

import androidx.lifecycle.ViewModel
import com.frozenproject.moviecatalogue.data.repository.CatalogueRepository
import com.frozenproject.moviecatalogue.internal.UnitSystem
import com.frozenproject.moviecatalogue.internal.lazyDefferred
import org.threeten.bp.LocalDate

class MovieListViewModel(
    private val repository: CatalogueRepository

):ViewModel() {

    val movieEntries by lazyDefferred {
        repository.getMovieCatalogueList()
    }

}