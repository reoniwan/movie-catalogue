package com.frozenproject.moviecatalogue.data.repository

import androidx.lifecycle.LiveData
import com.frozenproject.moviecatalogue.data.database.unit.movie.list.UnitSpecificMovieCatalogueEntry

interface CatalogueRepository {

    suspend fun getMovieCatalogueList(metric: Boolean): LiveData<out List<UnitSpecificMovieCatalogueEntry>>

}