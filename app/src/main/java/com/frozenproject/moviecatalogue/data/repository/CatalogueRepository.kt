package com.frozenproject.moviecatalogue.data.repository

import androidx.lifecycle.LiveData
import com.frozenproject.moviecatalogue.data.database.entity.ResultMovie
import com.frozenproject.moviecatalogue.data.database.unit.movie.list.MovieSpesificCatalogueEntry
import com.frozenproject.moviecatalogue.data.database.unit.movie.list.UnitSpecificMovieCatalogueEntry

interface CatalogueRepository {

    suspend fun getMovieCatalogueList(): LiveData<List<MovieSpesificCatalogueEntry>>


}