package com.frozenproject.moviecatalogue.data.repository

import androidx.lifecycle.LiveData
import com.frozenproject.moviecatalogue.data.database.MovieCatalogueDao
import com.frozenproject.moviecatalogue.data.database.unit.movie.list.UnitSpecificMovieCatalogueEntry
import com.frozenproject.moviecatalogue.data.network.CatalogueNetworkDataSource
import com.frozenproject.moviecatalogue.data.network.response.MovieCatalogueResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatalogueRepositoryImpl(
    catalogueNetworkDataSource: CatalogueNetworkDataSource,
    private val movieCatalogueDao: MovieCatalogueDao
): CatalogueRepository{

    init {
        catalogueNetworkDataSource.apply {
            downloadedCatalogueMovie.observeForever{newCatalogueMovie ->
                persistFetchedCatalogueMovie(newCatalogueMovie)
            }
        }
    }



    private fun persistFetchedCatalogueMovie(fetchedMovie: MovieCatalogueResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            val movieCatalogueList = fetchedMovie.resultsMovie
            movieCatalogueDao.insert(movieCatalogueList)
        }
    }

    override suspend fun getMovieCatalogueList(
        metric: Boolean
    ): LiveData<out List<UnitSpecificMovieCatalogueEntry>> {
       return withContext(Dispatchers.IO){
           return@withContext if (metric) movieCatalogueDao.getCatalogueResult()
           else movieCatalogueDao.getCatalogueResult()
       }
    }

}